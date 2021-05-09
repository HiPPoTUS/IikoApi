package com.example.iikoapi.entities

import com.example.iikoapi.entities.datatype.Product
import com.example.iikoapi.utils.OnItemClickListener


data class GroupProducts(
    val group: Int,
    val groupName: String,
    val products: List<Product>,
)
