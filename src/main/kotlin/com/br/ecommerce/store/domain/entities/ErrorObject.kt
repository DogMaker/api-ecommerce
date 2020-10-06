package com.br.ecommerce.store.domain.entities

data class ErrorObject (
    private val message: String,
    private val field: String,
    private val parameter: Any
)