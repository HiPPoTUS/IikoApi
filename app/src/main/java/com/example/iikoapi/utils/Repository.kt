package com.example.iikoapi.utils

import com.example.iikoapi.api.Api
import javax.inject.Inject

class Repository @Inject constructor(private val api: Api){

    suspend fun authentication() = api.authentication()

    suspend fun getOrganisations(token: String) = api.getOrganisations(token)

    suspend fun getMenu(id: String, token: String) = api.getMenu(id, token)

    suspend fun getRestrictions(token: String, id: String) = api.getRestrictions(token, id)
}