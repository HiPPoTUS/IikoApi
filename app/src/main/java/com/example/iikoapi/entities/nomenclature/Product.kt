package com.example.iikoapi.entities.nomenclature

import com.example.iikoapi.entities.datatype.Image
import com.example.iikoapi.entities.menu.GroupModifier
import com.example.iikoapi.entities.menu.GroupModifiersProduct
import com.example.iikoapi.entities.menu.Modifier

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
    val groupModifiers: List<GroupModifiersProduct>? = null,
    val modifiers: List<Modifier>? = null,
    val price: Long? = null,
    val weight: Double? = null,
    val images: List<Image?>? = null,
    val parentGroup: String,
    var hleb: List<Modifier>? = null
){
    fun setHleb(hleb: List<Modifier>?) = apply { this.hleb = hleb }
}