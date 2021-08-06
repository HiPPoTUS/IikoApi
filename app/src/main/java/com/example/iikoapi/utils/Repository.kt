package com.example.iikoapi.utils

import com.example.iikoapi.api.Api
import com.example.iikoapi.entities.profile.AuthorizationBodyRequest
import com.example.iikoapi.entities.profile.LoginRequest
import com.example.iikoapi.entities.start.MenuIdBody
import com.example.iikoapi.entities.start.OrganisationIdBody
import com.example.iikoapi.room.UserDao
import com.example.iikoapi.room.entity.User
import javax.inject.Inject

class Repository @Inject constructor(private val api: Api, private val userDao: UserDao){

//    suspend fun authentication() = api.authentication("vshaverma","n8mgiKG2")

    suspend fun getOrganisations() = api.getOrganisations()

    suspend fun getTerminals(organisationIdBody: OrganisationIdBody) = api.getTerminals(organisationIdBody)

    suspend fun getMenu(menuIdBody: MenuIdBody) = api.getMenu(menuIdBody)

    suspend fun registerUser(user: User) = api.authorization(AuthorizationBodyRequest(
        phone = user.phone!!,
        first_name = user.name!!,
        password = user.password!!
    ))

    suspend fun loginUser(loginRequest: LoginRequest) = api.login(loginRequest)

    suspend fun getUserInfo(token: String) = api.getUserInfo(token)

    suspend fun addUser(user: User) = userDao.insert(user)

    fun getUsers() = userDao.getUsers()

    suspend fun getUser() = userDao.getUser()

    suspend fun deleteUser(user: User) = userDao.deleteUser(user)

}