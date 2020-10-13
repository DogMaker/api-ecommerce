package com.br.ecommerce.store.services

import com.br.ecommerce.store.config.EnviomentConfig
import com.br.ecommerce.store.config.logger
import com.br.ecommerce.store.controller.ParametersRequestTableau
import com.br.ecommerce.store.domain.exceptions.AutenticationTableauException
import com.br.ecommerce.store.domain.exceptions.ConectionTableauException
import com.br.ecommerce.store.domain.interfaces.TableauService
import com.br.ecommerce.store.helpers.SecurityHandler
import com.br.ecommerce.store.model.entities.LoginResponseTableau
import com.br.ecommerce.store.model.entities.LoginTableau
import com.br.ecommerce.store.model.entities.Credentials
import com.br.ecommerce.store.model.entities.ContentUrl
import com.br.ecommerce.store.model.entities.Parameters
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.UnknownHostException
import org.slf4j.Logger

@Service
class TableauServiceImpl(val restTemplate: RestTemplate = RestTemplate(),
                         val config: EnviomentConfig = EnviomentConfig()) : TableauService {

    val logger: Logger = logger<TableauServiceImpl>()

    private val uri = config.tableauUri

    override fun authentication(): LoginResponseTableau? {
        val path = config.tableauPathAuth

        SecurityHandler.trustSelfSignedSSL()

        val loginTableau = LoginTableau(Credentials(config.tableauUser,
                config.tableauPass, ContentUrl("")))

        val headers = HttpHeaders()
        headers.add("accept", "application/json")

        val request: HttpEntity<LoginTableau> = HttpEntity(loginTableau, headers)

        return try {
            logger.info("Request auth token from tableau")
            val response = restTemplate.exchange("$uri$path", POST, request, LoginResponseTableau::class.java)
            response.body
        } catch (e: HttpStatusCodeException) {
            logger.error(AutenticationTableauException().message())
            throw AutenticationTableauException()
        } catch (e: UnknownHostException){
            logger.error(ConectionTableauException().message())
            throw ConectionTableauException()
        }
    }

    override fun retriveCsvData(token: String?, params: Parameters): String {
        val path = config.tableauPathCsv

        SecurityHandler.trustSelfSignedSSL()

        val headers = HttpHeaders()
        headers.add("accept", "application/json")
        headers.add("X-Tableau-Auth", token)

        val uriBuilder = UriComponentsBuilder.fromHttpUrl("$uri$path")
                .queryParam("vf_locale", params.locale)
                .queryParam("vf_Start Date B", params.startDateB)
                .queryParam("vf_End Date B", params.endDateB)
                .queryParam("vf_Start Date A", params.startDateA)
                .queryParam("vf_End Date A", params.endDateA)
                .queryParam("vf_Choose a Metric", params.metric)

        val header: HttpEntity<String> = HttpEntity(headers)
        return try {
            logger.info("Request csv data from tableau")
            val response =  restTemplate.exchange(uriBuilder.toUriString(), GET, header, String::class.java)
            response.body.toString()
        } catch (e: UnknownHostException){
            logger.error(ConectionTableauException().message())
            throw ConectionTableauException()
        }
    }

    override fun getData(params: ParametersRequestTableau):String{
        val authToken = TableauServiceImpl().authentication()?.credentials?.token
        logger.info("Construct the parameter url")
        val parameters = Parameters(
                "${params.localeUnderAnalisys},${params.localeBenchmark}",
                params.startdateB, params.enddateB,
                params.startdateA, params.enddateA,
                params.metricAnalisys
        )
        logger.info("Created parameters ${parameters}")
        return TableauServiceImpl().retriveCsvData(authToken, parameters)
    }
}

