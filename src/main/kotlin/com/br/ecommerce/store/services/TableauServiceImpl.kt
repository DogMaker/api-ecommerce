package com.br.ecommerce.store.services

import com.br.ecommerce.store.domain.exceptions.AutenticationTableauException
import com.br.ecommerce.store.domain.exceptions.ConectionTableauException
import com.br.ecommerce.store.domain.interfaces.TableauService
import com.br.ecommerce.store.helpers.SecurityHandler
import com.br.ecommerce.store.model.entities.*
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.UnknownHostException


@Service
class TableauServiceImpl(val restTemplate: RestTemplate = RestTemplate()) : TableauService {

    private val uri = "https://run.mocky.io/v3"

    override fun authentication(): LoginResponseTableau? {
        val path = "/a0d4d954-5e06-42df-97f1-392cc723c723"

        SecurityHandler.trustSelfSignedSSL()

        val loginTableau = LoginTableau(Credentials("hugsil", "", ContentUrl("")))

        val headers = HttpHeaders()
        headers.add("accept", "application/json")

        val request: HttpEntity<LoginTableau> = HttpEntity(loginTableau, headers)

        return try {
            val response = restTemplate.exchange("$uri$path", POST, request, LoginResponseTableau::class.java)
            response.body
        } catch (e: HttpStatusCodeException) {
            throw AutenticationTableauException()
        } catch (e: UnknownHostException){
            throw ConectionTableauException()
        }
    }

    override fun retriveCsvData(token: String?, params: Parameters): String {
        val path = "/2220fc04-ac9c-4123-b4e1-b3b628cf107d"

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
            val response =  restTemplate.exchange(uriBuilder.toUriString(), GET, header, String::class.java)
            response.body.toString()
        } catch (e: UnknownHostException){
            throw ConectionTableauException()
        }
    }
}

fun main() {
    val authToken = TableauServiceImpl().authentication()?.credentials?.token
    val parameters = Parameters("pt_BR,en_US", "2020-06-25", "2020-06-26", "2020-06-26", "2020-06-26", "GSR")
    println(TableauServiceImpl().retriveCsvData(authToken, parameters))
}
