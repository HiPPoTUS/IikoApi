package com.example.iikoapi.entities.order

import com.example.iikoapi.entities.start.OrganisationIdBody

data class Order(
    val organizationId: String = OrganisationIdBody().organizationId!!,
    val items: List<OrderItem>,
    val fullSum: Int
)