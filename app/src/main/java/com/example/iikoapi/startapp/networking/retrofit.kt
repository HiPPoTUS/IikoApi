package com.example.iikoapi.startapp.networking

import android.location.Address
import com.example.dodocopy.dataTypes.CityWithStreets
import com.example.iikoapi.startapp.datatype.*
import org.json.JSONObject
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
//
    @GET("rmsSettings/getPaymentTypes?")
    fun get_payment_types(
        @Query("access_token") token: String?,
        @Query("organization") org: String?
    ):Call<paymentTypesResponse>

    @GET("cities/cities?")
    fun get_streets(
        @Query("access_token") token: String?,
        @Query("organization") org: String?
    ): Call<ArrayList<CityWithStreets>?>?

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

    @Headers("Content-type:application/json")
    @POST("add?/")
    fun post_order(
        @Query("access_token") token: String?,
        @Body order:OrderRequest
    ):Call<OrderInfo>

    @Headers("Content-type:application/json")
    @POST("/orders/checkCreate?")
    fun check_order (
        @Query("access_token") token: String?,
        @Body order:OrderRequest
    ):Call<OrderChecksCreationResult>
//
    @Headers("Content-type:application/json")
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

