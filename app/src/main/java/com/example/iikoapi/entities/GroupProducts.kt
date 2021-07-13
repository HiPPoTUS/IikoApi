package com.example.iikoapi.entities

import com.example.iikoapi.entities.nomenclature.Product


data class GroupProducts(
    val groupIndex: Int,
    val groupName: String,
    val products: List<Product>
)
