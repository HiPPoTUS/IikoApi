package com.example.iikoapi.modules

import android.app.Application
import com.example.iikoapi.api.Api
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.main.MainViewModelFactory
import com.example.iikoapi.utils.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MVVMModule {

    @Singleton
    @Provides
    fun provideRepository(api: Api): Repository {
        return Repository(api)
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

//    @Provides
//    fun provideStartFragmentViewModel(repository: Repository, mainViewModel: MainViewModel): StartFragmentViewModel {
//        return StartFragmentViewModel(repository, mainViewModel)
//    }
//
//    @Provides
//    fun provideStartFragmentViewModelFactory(startFragmentViewModel: StartFragmentViewModel): StartFragmentViewModelFactory {
//        return StartFragmentViewModelFactory(startFragmentViewModel)
//    }

}