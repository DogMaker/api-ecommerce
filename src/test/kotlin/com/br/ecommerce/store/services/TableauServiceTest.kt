package com.br.ecommerce.store.services

import com.br.ecommerce.store.config.EnviomentConfig
import com.br.ecommerce.store.domain.exceptions.AutenticationTableauException
import com.br.ecommerce.store.domain.exceptions.ConectionTableauException
import com.br.ecommerce.store.model.entities.*
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.UnknownHostException


@ExtendWith(MockKExtension::class)
class TableauServiceTest {

    var restTemplate = mockk<RestTemplate>()
    private val config = EnviomentConfig()
    private val authEndpoint = "${config.tableauUri}${config.tableauPathAuth}"
    private val csvEndpoint =  "${config.tableauUri}${config.tableauPathCsv}"

    @Test
    fun `given valid credentials should return a response payload object with success`() {
        every {
            restTemplate.exchange(authEndpoint,
                    HttpMethod.POST, any(), LoginResponseTableau::class.java)
        } returns mockResponse()

        val expected = LoginResponseTableau(
                CredentialsResponse(
                        Site("abx", ""),
                        User("user01"), "abx")
        )

        val actual = TableauServiceImpl(restTemplate).authentication()
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `given invalid credentials should return an exception AutenticationTableauException`() {
        every {
            restTemplate.exchange(authEndpoint,
                    HttpMethod.POST, any(), LoginResponseTableau::class.java)
        } throws HttpClientErrorException(HttpStatus.UNAUTHORIZED)

        assertThatExceptionOfType(AutenticationTableauException::class.java)
                .isThrownBy { TableauServiceImpl(restTemplate).authentication() }
    }

    @Test
    fun `given invalid payload should return an exception ConectionTableauException`() {
        every {
            restTemplate.exchange(authEndpoint,
                    HttpMethod.POST, any(), LoginResponseTableau::class.java)
        } throws UnknownHostException()

        assertThatExceptionOfType(ConectionTableauException::class.java)
                .isThrownBy { TableauServiceImpl(restTemplate).authentication() }
    }

    @Test
    fun `given valid token and params should return a response a csv format with success`() {

        every {
            restTemplate.exchange(uriBuilderRetriveData.toUriString(),
                    HttpMethod.GET, any(), String::class.java)
        } returns mockResponseString()

        val parameters = Parameters("pt_BR,en_US", "2020-06-25",
                "2020-06-26", "2020-06-26", "2020-06-26", "GSR")
        val authToken = "XXXXXXXXXXXXX"

        val actual = TableauServiceImpl(restTemplate).retriveCsvData(authToken, parameters)

        assertThat(actual).asString()
    }

    @Test
    fun `given a problem with conection return an exception ConectionTableauException`() {

        every {
            restTemplate.exchange(uriBuilderRetriveData.toUriString(),
                    HttpMethod.GET, any(), String::class.java)
        } throws UnknownHostException()

        val parameters = Parameters("pt_BR,en_US", "2020-06-25",
                "2020-06-26", "2020-06-26", "2020-06-26", "GSR")
        val authToken = "XXXXXXXXXXXXX"

        assertThatExceptionOfType(ConectionTableauException::class.java)
                .isThrownBy { TableauServiceImpl(restTemplate).retriveCsvData(authToken, parameters) }
    }

    private fun mockResponse(): ResponseEntity<LoginResponseTableau> {
        val header = HttpHeaders()
        header.contentType = MediaType.APPLICATION_JSON

        return ResponseEntity(
                LoginResponseTableau(CredentialsResponse(
                        Site("abx", ""),
                        User("user01"), "abx")
                ),
                header,
                HttpStatus.OK
        )
    }

    private fun mockResponseString(): ResponseEntity<String> {
        val header = HttpHeaders()
        header.contentType = MediaType.APPLICATION_JSON

        return ResponseEntity(
                "CSV",
                header,
                HttpStatus.OK
        )
    }

    private val uriBuilderRetriveData = UriComponentsBuilder.fromHttpUrl(csvEndpoint)
            .queryParam("vf_locale", "pt_BR,en_US")
            .queryParam("vf_Start Date B", "2020-06-25")
            .queryParam("vf_End Date B", "2020-06-26")
            .queryParam("vf_Start Date A", "2020-06-26")
            .queryParam("vf_End Date A", "2020-06-26")
            .queryParam("vf_Choose a Metric", "GSR")
}
