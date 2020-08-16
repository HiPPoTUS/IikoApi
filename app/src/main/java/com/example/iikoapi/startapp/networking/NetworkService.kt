package com.example.iikoapi.startapp.networking

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class iiko_NetworkService private constructor() {
    private val mRetrofit: Retrofit
    val iikoApi: IIKO_API
        get() = mRetrofit.create<IIKO_API>(IIKO_API::class.java)

    companion object {
        private var mInstance: iiko_NetworkService? = null
        private const val BASE_URL = "https://iiko.biz:9900/api/0/"
        val instance: iiko_NetworkService?
            get() {
                if (mInstance == null) {
                    mInstance = iiko_NetworkService()
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

class cp_NetworkService private constructor() {
    private val mRetrofit: Retrofit
    val cpApi: PayMethods
        get() = mRetrofit.create<PayMethods>(PayMethods::class.java)

    companion object {
        private var mInstance: cp_NetworkService? = null
        private const val BASE_URL = "http://localhost:80/"
        val instance: cp_NetworkService?
            get() {
                if (mInstance == null) {
                    mInstance = cp_NetworkService()
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

