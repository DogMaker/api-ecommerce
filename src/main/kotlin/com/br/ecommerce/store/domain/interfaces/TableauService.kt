package com.br.ecommerce.store.model.interfaces

import com.br.ecommerce.store.model.entities.LoginResponseTableau
import com.br.ecommerce.store.model.entities.Parameters

interface TableauService{

     fun authentication(): LoginResponseTableau

     fun retriveCsvData(token: String?, params : Parameters): String
}


