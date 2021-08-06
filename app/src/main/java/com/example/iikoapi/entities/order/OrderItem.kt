package com.example.iikoapi.entities.order

data class OrderItem(
    val amount: Int,
    val code: String,
    val id: String,
    val modifiers: List<OrderModifier>,
    val name: String,
    val sum: Int
)