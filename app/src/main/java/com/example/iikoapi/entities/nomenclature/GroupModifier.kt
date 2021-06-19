package com.example.iikoapi.entities.nomenclature

data class GroupModifier (
    val maxAmount: Long? = null,
    val minAmount: Long? = null,
    val modifierId: String? = null,
    val required: Boolean? = null,
    val childModifiers: List<Modifier>? = null,
    val childModifiersHaveMinMaxRestrictions: Boolean? = null
)