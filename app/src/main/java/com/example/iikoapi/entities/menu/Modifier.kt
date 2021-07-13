package com.example.iikoapi.entities.menu

import com.example.iikoapi.entities.SelectableEntity

data class Modifier(
    val id: String,
    val name: String,
    val parentGroup: String,
    val maxAmount: Long? = null,
    val minAmount: Long? = null,
    val modifierId: String? = null,
    val required: Boolean? = null,
    val defaultAmount: Long? = null,
    val hideIfDefaultAmount: Boolean? = null,
    val price: String
): SelectableEntity() {
    override var isSelected: Boolean = false
}