package com.example.iikoapi.entities.nomenclature

data class Group (
    val id: String? = null,
    val name: String? = null,
    val products: List<Product>? = null,
    val order: Int? = null,
    val parentGroup: Any? = null
)