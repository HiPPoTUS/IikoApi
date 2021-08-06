package com.example.iikoapi.profile

data class UserInfo(
    val id: Int,
    val phone: String?,
    val email: String?,
    val first_name: String?,
    val last_name: String?,
    val role: String?,
    val bio: String?,
    val image: String?,
    val date_of_birth: String?
)