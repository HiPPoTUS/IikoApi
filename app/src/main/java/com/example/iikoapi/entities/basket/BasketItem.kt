package com.example.iikoapi.entities.basket

import com.example.iikoapi.entities.MerchItem
import com.example.iikoapi.entities.menu.Modifier
import com.example.iikoapi.entities.nomenclature.Product


data class BasketItem(
    val product: Product,
    val modifiers: List<MerchItem>,
    val removeModifiers: List<Modifier>? = null
)