package com.example.iikoapi.entities.order

data class OrderModifier(
    val amount: Int = 1,
    val groupId: String,
    val groupName: String? = null,
    val id: String,
    val name: String,
    val price: Int
)