package com.br.ecommerce.store.model.entities

data class Login(
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

data class LoginResponse(
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