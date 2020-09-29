package com.br.ecommerce.store.model.interfaces

import com.br.ecommerce.store.model.entities.LoginResponse

interface RequestInterface{

     fun loginTableau(): LoginResponse

     fun retriveCsvData(): String
}


