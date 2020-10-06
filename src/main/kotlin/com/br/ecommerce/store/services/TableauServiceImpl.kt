package com.br.ecommerce.store.service

import com.br.ecommerce.store.domain.exceptions.AutenticationTableauException
import com.br.ecommerce.store.domain.exceptions.ConectionTableauException
import com.br.ecommerce.store.helpers.SecurityHandler
import com.br.ecommerce.store.model.entities.*
import com.br.ecommerce.store.model.interfaces.TableauService
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.UnknownHostException

@Service
class TableauServiceImpl : TableauService {
    private val uri = "https://run.mocky.io/v3/2220fc04-ac9c-4123-b4e1-b3b628cf107d"

    private val restTemplate = RestTemplate()

    override fun authentication(): LoginResponseTableau {
        SecurityHandler.trustSelfSignedSSL()

        val loginTableau = LoginTableau(Credentials("hugsil", "", ContentUrl("")))

        val headers = HttpHeaders()
        headers.add("accept", "application/json")

        val request: HttpEntity<LoginTableau> = HttpEntity(loginTableau, headers)

        return try {
            restTemplate.postForObject(uri, request, LoginResponseTableau::class.java)!!
        } catch (e: HttpStatusCodeException) {
            throw AutenticationTableauException()
        } catch (e: UnknownHostException){
            throw ConectionTableauException()
        }
    }
    override fun retriveCsvData(token: String?, params : Parameters): String {

        val uri2 = "https://skynet.db.amazon.com/api/3.1/sites/a946d998-2ead-4894-bb50-1054a91dcab3/views/79db6728-eaa6-4b98-9897-6898d70b6c82/data"

        SecurityHandler.trustSelfSignedSSL()
        val headers = HttpHeaders()
        headers.add("accept", "application/json")
        headers.add("X-Tableau-Auth", token)

        val builder = UriComponentsBuilder.fromHttpUrl(uri2)
                .queryParam("vf_locale", params.locale)
                .queryParam("vf_Start Date B", params.startDateB)
                .queryParam("vf_End Date B", params.endDateB)
                .queryParam("vf_Start Date A", params.startDateA)
                .queryParam("vf_End Date A", params.endDateA)
                .queryParam("vf_Choose a Metric", params.metric)

        val header: HttpEntity<String> = HttpEntity(headers)
        val response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, header, String::class.java)

        return response.toString()
    }
}

fun main() {
    val authToken = TableauServiceImpl().authentication().credentials?.token
    val parameters = Parameters("pt_BR,en_US","2020-06-25","2020-06-26","2020-06-26","2020-06-26","GSR")
    println(TableauServiceImpl().retriveCsvData(authToken, parameters))
}
