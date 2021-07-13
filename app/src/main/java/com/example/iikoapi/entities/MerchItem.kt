package com.example.iikoapi.entities

data class MerchItem(
    val id: Int,
    val url: String
): SelectableEntity() {
    override var isSelected: Boolean = false
}