package com.example.iikoapi.entities.start

import java.io.Serializable

data class Terminal (
    val deliveryTerminalId: String,
    val organizationId: String,
    val deliveryRestaurantName: String,
    val deliveryGroupName: String,
    val name: String,
    val openingHours: List<Any?>,
    val protocolVersion: Long,
    val address: String,
    val workTime: Any?,
    val externalRevision: Long,
    val technicalInformation: Any?
): Serializable