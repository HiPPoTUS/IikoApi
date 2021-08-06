package com.example.iikoapi.entities.profile

data class AuthorizationBodyRequest(
    val phone: String,
    val first_name: String,
    val password: String
)