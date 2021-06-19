package com.example.iikoapi.entities.nomenclature

data class Modifier (
    val maxAmount: Long? = null,
    val minAmount: Long? = null,
    val modifierId: String? = null,
    val required: Boolean? = null,
    val defaultAmount: Long? = null,
    val hideIfDefaultAmount: Boolean? = null,
    val products: List<Product>? = null
)