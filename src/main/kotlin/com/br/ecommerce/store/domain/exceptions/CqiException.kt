package com.br.ecommerce.store.domain.exceptions

import java.lang.Exception

abstract class CqiException : Exception{

    constructor() : super()
    abstract fun message(): String

}

data class Error(val message: String)