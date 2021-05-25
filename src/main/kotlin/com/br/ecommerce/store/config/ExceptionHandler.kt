package com.br.ecommerce.store.config

import com.br.ecommerce.store.domain.exceptions.AutenticationTableauException
import com.br.ecommerce.store.domain.exceptions.ConectionTableauException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime


@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AutenticationTableauException::class)
    fun handleAuthenticationException(ex: AutenticationTableauException?): ResponseEntity<Any> {
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = AutenticationTableauException().message()
        return ResponseEntity(body, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ConectionTableauException::class)
    fun handleConectionTableauExceptionException(ex: AutenticationTableauException?): ResponseEntity<Any> {
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ConectionTableauException().message()
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }
}
