package com.br.ecommerce.store.service

import com.br.ecommerce.store.model.entities.*
import com.br.ecommerce.store.model.interfaces.RequestInterface
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate


class TableauService : RequestInterface{
    private val uri = "https://run.mocky.io/v3/2220fc04-ac9c-4123-b4e1-b3b628cf107d"
    private val restTemplate = RestTemplate()

    override fun loginTableau(): LoginResponseTableau {

        val loginTableau = LoginTableau(Credentials("","",ContentUrl("")))

        val headers = HttpHeaders()
        headers.add("accept", "application/json")

        val request: HttpEntity<LoginTableau> = HttpEntity<LoginTableau>(loginTableau, headers)
        val response: LoginResponseTableau? =  restTemplate.postForObject(uri, request, LoginResponseTableau::class.java)

        return response!!
    }

    override fun retriveCsvData(): String{
        val headers = HttpHeaders()
        headers.add("accept", "application/json")

        val header: HttpEntity<String> = HttpEntity<String>(headers)

        val response = restTemplate.exchange(uri, HttpMethod.GET, header, String::class.java)

        return response.toString()
    }
}

