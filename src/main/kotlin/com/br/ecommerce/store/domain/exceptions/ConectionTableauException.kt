package com.br.ecommerce.store.domain.exceptions

class ConectionTableauException : CqiException() {
    override fun message() = "There was an error with the tableau server"
}
