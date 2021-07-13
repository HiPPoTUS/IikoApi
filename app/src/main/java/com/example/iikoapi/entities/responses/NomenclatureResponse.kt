package com.example.iikoapi.entities.responses

import com.example.iikoapi.entities.start.mapper
import com.example.iikoapi.entities.nomenclature.Group
import com.fasterxml.jackson.module.kotlin.readValue

data class NomenclatureResponse (
    val groups: List<Group>? = null,
    val revision: Long? = null,
    val uploadDate: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<NomenclatureResponse>(json)
    }
}