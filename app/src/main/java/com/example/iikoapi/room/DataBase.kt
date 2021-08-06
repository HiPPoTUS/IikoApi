package com.example.iikoapi.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.iikoapi.room.entity.User

@Database(entities = [User::class], version = 1)
abstract class DataBase: RoomDatabase(){

    abstract fun userDao(): UserDao

    companion object{
        val NAME = "DATABASE"
    }
}