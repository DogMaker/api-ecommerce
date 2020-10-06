package com.br.ecommerce.store.model.entities

data class LoginTableau(
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

data class LoginResponseTableau(
        val credentials : CredentialsResponse
)

data class CredentialsResponse (
        val site : Site,
        val user : User,
        val token : String
)

data class Site (
        val id : String,
        val contentUrl : String
)

data class User (
        val id : String
)

data class Parameters (
        val locale: String?,
        val startDateB: String?,
        val endDateB: String?,
        val startDateA: String?,
        val endDateA: String?,
        val metric: String?
)