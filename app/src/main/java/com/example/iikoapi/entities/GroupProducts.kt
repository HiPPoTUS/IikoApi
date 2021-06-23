package com.example.iikoapi.entities

import com.example.iikoapi.entities.datatype.Product
import com.example.iikoapi.utils.OnItemClickListener
import com.google.gson.annotations.SerializedName


data class GroupProducts(
    val group: Int,
    val groupName: String,
    val products: List<Product>,
)
