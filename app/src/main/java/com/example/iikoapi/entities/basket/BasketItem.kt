package com.example.iikoapi.entities.basket

import com.example.iikoapi.entities.nomenclature.Product


data class BasketItem(
    val product: Product,
    val modifiers: List<String>
)