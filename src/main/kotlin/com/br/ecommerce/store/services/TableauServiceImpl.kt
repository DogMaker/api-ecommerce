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
import java.net.UnknownHostException

@Service
class TableauServiceImpl : TableauService {
    private val uri = "https://run.mocky.io/v3/2220fc04-ac9c-4123-b4e1-b3b628cf107d"

    private val restTemplate = RestTemplate()

    override fun authentication(): LoginResponseTableau {
        SecurityHandler.trustSelfSignedSSL()

        val loginTableau = LoginTableau(Credentials("hugsil", "#Camelo19*%", ContentUrl("")))

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
    override fun retriveCsvData(): String{
        val headers = HttpHeaders()
        headers.add("accept", "application/json")

        val header: HttpEntity<String> = HttpEntity<String>(headers)

        val response = restTemplate.exchange(uri, HttpMethod.GET, header, String::class.java)

        return response.toString()
    }
}

fun main(){
    print(TableauServiceImpl().authentication().credentials.token)
}

