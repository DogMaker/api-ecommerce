package com.br.ecommerce.store.model.entities

data class Tableau(
        val credentials :Credentials
)

data class Credentials(
        val personalAccessTokenName :String,
        val personalAccessTokenSecret : String,
        val site: ContentUrl
)

data class ContentUrl(
        val contentUrl:String
)
