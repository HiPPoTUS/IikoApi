package com.example.iikoapi.api

import com.example.iikoapi.entities.menu.Menu
import com.example.iikoapi.entities.nomenclature.Product
import com.example.iikoapi.entities.profile.AuthorizationBodyRequest
import com.example.iikoapi.entities.profile.AuthorizationBodyResponse
import com.example.iikoapi.entities.profile.LoginRequest
import com.example.iikoapi.entities.profile.LoginResponse
import com.example.iikoapi.entities.start.MenuIdBody
import com.example.iikoapi.entities.start.OrganisationIdBody
import com.example.iikoapi.entities.start.Terminal
import com.example.iikoapi.profile.UserInfo
import retrofit2.http.*


interface Api {

    @GET("v1/organizations/")
    suspend fun getOrganisations(): String

    @POST("v1/terminals/")
    suspend fun getTerminals(@Body organisationIdBody: OrganisationIdBody): List<Terminal>

    @POST("v1/menu_terminal/")
    suspend fun getMenu(@Body menuId: MenuIdBody): Menu

    @POST("v1/users/")
    suspend fun authorization(@Body body: AuthorizationBodyRequest): AuthorizationBodyResponse

    @POST("v1/auth/token/login/")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @GET("v1/users/me/")
    suspend fun getUserInfo(@Header("Authorization") token: String): UserInfo

}