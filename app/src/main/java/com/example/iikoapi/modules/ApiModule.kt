package com.example.iikoapi.modules

import android.app.Application
import com.example.iikoapi.api.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideApi(): Api {
        return Retrofit.Builder()
            .baseUrl("https://iiko.biz:9900/api/0/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
    }
}