package com.example.iikoapi.entities

data class MerchItem(
    val id: Int,
    val url: String,
    var isSelected: Boolean = false
)