package com.example.iikoapi.modules

import android.content.Context
import androidx.room.Room
import com.example.iikoapi.room.DataBase
import com.example.iikoapi.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideDataBase(@ApplicationContext context: Context):DataBase{
        return Room.databaseBuilder(context, DataBase::class.java, DataBase.NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(dataBase: DataBase): UserDao {
        return dataBase.userDao()
    }

}