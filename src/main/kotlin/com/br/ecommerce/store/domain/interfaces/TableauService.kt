package com.br.ecommerce.store.domain.interfaces

import com.br.ecommerce.store.model.entities.LoginResponseTableau
import com.br.ecommerce.store.model.entities.Parameters
import org.springframework.http.ResponseEntity

interface TableauService{

     fun authentication(): LoginResponseTableau?

     fun retriveCsvData(token: String?, params : Parameters): String
}


