package com.br.ecommerce.store.domain.exceptions

class AutenticationTableauException : CqiException() {
    override fun message() = "There was an error with authentication with tableau"
}


