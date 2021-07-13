package com.example.iikoapi.entities.menu

data class GroupModifiersProduct(
    val modifierId: String,
    val maxAmount: Int,
    val required: Boolean,
    val childModifiers: List<ChildModifier>
)