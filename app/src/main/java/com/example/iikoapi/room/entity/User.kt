package com.example.iikoapi.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo
    var name: String?,

    @ColumnInfo
    var phone: String?,

    @ColumnInfo
    var token: String? = null,

    val password: String? = null

)