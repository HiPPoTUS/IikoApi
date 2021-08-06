package com.example.iikoapi.modules

import android.content.Context
import com.example.iikoapi.api.Api
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.main.MainViewModelFactory
import com.example.iikoapi.room.UserDao
import com.example.iikoapi.utils.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MVVMModule {

    @Singleton
    @Provides
    fun provideRepository(api: Api, userDao: UserDao): Repository {
        return Repository(api, userDao)
    }

    @Singleton
    @Provides
    fun provideMainViewModel(repository: Repository): MainViewModel {
        return MainViewModel(repository)
    }

    @Singleton
    @Provides
    fun provideMainViewModelFactory(mainViewModel: MainViewModel): MainViewModelFactory {
        return MainViewModelFactory(mainViewModel)
    }

}