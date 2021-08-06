package com.example.iikoapi.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.iikoapi.room.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM User")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM User")
    suspend fun getUser(): List<User>

    @Delete
    suspend fun deleteUser(user: User)
}