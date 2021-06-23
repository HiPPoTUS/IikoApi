package com.example.iikoapi.entities.nomenclature

data class Product (
    val additionalInfo: Any? = null,
    val description: String? = null,
    val id: String? = null,
    val name: String? = null,
    val carbohydrateAmount: Double? = null,
    val carbohydrateFullAmount: Double? = null,
    val energyAmount: Double? = null,
    val energyFullAmount: Double? = null,
    val fatAmount: Double? = null,
    val fatFullAmount: Double? = null,
    val fiberAmount: Double? = null,
    val fiberFullAmount: Double? = null,
    val groupModifiers: List<GroupModifier>? = null,
    val modifiers: List<Modifier>? = null,
    val price: Long? = null,
    val weight: Double? = null,
    val images: List<Any?>? = null
)