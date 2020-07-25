package com.example.iikoapi.startapp.networking

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class NetworkService private constructor() {
    private val mRetrofit: Retrofit
    val iikoApi: IIKO_API
        get() = mRetrofit.create<IIKO_API>(IIKO_API::class.java)

    companion object {
        private var mInstance: NetworkService? = null
        private const val BASE_URL = "https://iiko.biz:9900/api/0/"
        val instance: NetworkService?
            get() {
                if (mInstance == null) {
                    mInstance = NetworkService()
                }
                return mInstance
            }
    }
    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}


