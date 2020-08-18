package com.example.iikoapi.startapp.networking

import android.location.Address
import com.example.iikoapi.startapp.datatype.*
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
    fun get_terminals(
        @Query("access_token") token: String?,
        @Query("organization") org: String?
    ): Call<List<DeliveryTerminal>?>?

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

    @POST("add?/")
    fun post_order(
        @Query("access_token") token: String?,
        @Body order:OrderRequest
    ):Call<OrderInfo>

    @POST("/orders/checkCreate?")
    fun check_order (
        @Query("access_token") token: String?,
        @Body order:OrderRequest
    ):Call<OrderChecksCreationResult>
//
    @POST("orders/checkAddress?")
    fun check_delivery(
        @Query("access_token") token: String?,
        @Query("organizationId") org: String?,
        @Body addr: com.example.dodocopy.dataTypes.Address
    ):Call<AddressCheckResult>
}

interface PayMethods {
    @POST("cp_charge.php")
    fun charge(
        @Header("Content-Type") contentType:String,
        @Body args:PayRequestArgs
    ):Call<PayApiResponse<Transaction>>

    @POST("cp_post3ds.php")
    fun post3ds(
        @Header("Content-Type") contentType:String,
        @Body args:Post3dsRequestArgs
    ):Call<PayApiResponse<Transaction>>
}

