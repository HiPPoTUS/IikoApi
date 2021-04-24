package com.example.iikoapi.startapp.networking

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class IikoNetworkService private constructor() {
    private val mRetrofit: Retrofit
    val iikoApi: IIKO_API
        get() = mRetrofit.create<IIKO_API>(IIKO_API::class.java)

    companion object {
        private var mInstance: IikoNetworkService? = null
        private const val BASE_URL = "https://iiko.biz:9900/api/0/"
//        private const val BASE_URL = "https://vkusnaya-shaverma-co.iiko.it:9900/api/0/"
        val instance: IikoNetworkService?
            get() {
                if (mInstance == null) {
                    mInstance = IikoNetworkService()
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

class CpNetworkService private constructor() {
    private val mRetrofit: Retrofit
    val cpApi: PayMethods
        get() = mRetrofit.create<PayMethods>(PayMethods::class.java)

    companion object {
        private var mInstance: CpNetworkService? = null
        private const val BASE_URL = "http://192.168.0.100:80/"
        val instance: CpNetworkService?
            get() {
                if (mInstance == null) {
                    mInstance = CpNetworkService()
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

