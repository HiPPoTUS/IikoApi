package com.example.iikoapi.startapp.networking

import com.example.iikoapi.startapp.datatype.OrganisationInfo
import retrofit2.Call
import retrofit2.http.*

interface IIKO_API {
    @GET("auth/access_token?")
    fun auth(
        @Query("user_id") id: String,
        @Query("user_secret") pw: String
    ): Call<String?>?

    @GET("organization/list?")
    fun organisations(
        @Query("access_token") token: String?
    ): Call<ArrayList<OrganisationInfo>>?

    @GET("deliverySettings/getDeliveryRestrictions?")
    fun restrictions(
        @Query("access_token") token: String?,
        @Query("organization") org: String?
    ): Call<DeliveryRestrictionsResponse?>?

    @GET("nomenclature/{org}")
    fun menu(
        @Path("org") orgID: String?,
        @Query("access_token") token: String?
    ): Call<MenuResponse?>?
}

