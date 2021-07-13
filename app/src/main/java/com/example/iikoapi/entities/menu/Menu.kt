package com.example.iikoapi.entities.menu

import com.example.iikoapi.entities.nomenclature.Product

data class Menu(
    val groups_products : List<Group>,
    val products: List<Product>,
    val modifiers: List<Modifier>,
    val groups_modifiers: List<GroupModifier>
)