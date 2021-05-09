package com.example.iikoapi.api

import com.example.iikoapi.entities.OrganisationInfo
import com.example.iikoapi.entities.DeliveryRestrictionsResponse
import com.example.iikoapi.entities.MenuResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("auth/access_token?")
    suspend fun authentication(
        @Query("user_id") id: String, @Query("user_secret") pw: String
    ): String

    @GET("organization/list?")
    suspend fun getOrganisations(
        @Query("access_token") token: String?
    ): ArrayList<OrganisationInfo>

    @GET("nomenclature/{org}")
    suspend fun getMenu(
        @Path("org") orgId: String?,
        @Query("access_token") token: String?
    ): MenuResponse

    @GET("deliverySettings/getDeliveryRestrictions?")
    suspend fun getRestrictions(
        @Query("access_token") token: String?,
        @Query("organization") org: String?
    ): DeliveryRestrictionsResponse?
}