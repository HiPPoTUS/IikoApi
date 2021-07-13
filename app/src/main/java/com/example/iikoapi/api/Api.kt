package com.example.iikoapi.api

import com.example.iikoapi.entities.menu.Menu
import com.example.iikoapi.entities.nomenclature.Product
import com.example.iikoapi.entities.start.MenuIdBody
import com.example.iikoapi.entities.start.OrganisationIdBody
import com.example.iikoapi.entities.start.Terminal
import retrofit2.http.*


interface Api {

//    @GET("auth/access_token?")
//    suspend fun authentication(
//        @Query("user_id") id: String, @Query("user_secret") pw: String
//    ): String
//
    @GET("v1/update_organizations/")
    suspend fun getOrganisations(): String
//
    @POST("v1/terminals/")
    suspend fun getTerminals(@Body organisationIdBody: OrganisationIdBody): List<Terminal>

    @POST("v1/menu_terminal/")
    suspend fun getMenu(@Body menuId: MenuIdBody): Menu
//
//    @GET("deliverySettings/getDeliveryRestrictions?")
//    suspend fun getRestrictions(
//        @Query("access_token") token: String?,
//        @Query("organization") org: String?
//    ): DeliveryRestrictionsResponse?
}