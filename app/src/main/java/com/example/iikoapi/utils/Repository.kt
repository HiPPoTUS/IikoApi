package com.example.iikoapi.utils

import com.example.iikoapi.api.Api
import com.example.iikoapi.entities.start.MenuIdBody
import com.example.iikoapi.entities.start.OrganisationIdBody
import javax.inject.Inject

class Repository @Inject constructor(private val api: Api){

//    suspend fun authentication() = api.authentication("vshaverma","n8mgiKG2")

    suspend fun getOrganisations() = api.getOrganisations()

    suspend fun getTerminals(organisationIdBody: OrganisationIdBody) = api.getTerminals(organisationIdBody)

    suspend fun getMenu(menuIdBody: MenuIdBody) = api.getMenu(menuIdBody)
//
//    suspend fun getRestrictions(token: String, id: String) = api.getRestrictions(token, id)

}