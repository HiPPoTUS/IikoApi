package com.example.iikoapi.entities.start
import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.*
import java.io.Serializable

val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

class DistrictResponse(elements: Map<String, List<District>>) : HashMap<String, List<District>>(elements) {
    fun toJson() = mapper.writeValueAsString(this)
    companion object {
        fun fromJson(json: String) = mapper.readValue<DistrictResponse>(json)
    }
}

data class District(
    val adr: String? = null,
    val phone: String? = null,
    val aff: String? = null,
    val distr: String? = null,
    val lat: Double? = null,
    val lng: Double? = null
): Serializable

