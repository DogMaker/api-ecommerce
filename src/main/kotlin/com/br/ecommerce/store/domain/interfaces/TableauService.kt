package com.br.ecommerce.store.model.interfaces

import com.br.ecommerce.store.model.entities.LoginResponseTableau

interface TableauService{

     fun authentication(): LoginResponseTableau

     fun retriveCsvData(): String
}


