package com.example.iikoapi.utils

import com.example.iikoapi.entities.menu.Menu
import com.example.iikoapi.entities.profile.AuthorizationBodyResponse
import com.example.iikoapi.entities.profile.LoginResponse
import com.example.iikoapi.entities.start.Terminals

sealed class LoadingState {

    object Loading : LoadingState()

    class SuccessMenu(val menu: Menu) : LoadingState()

    class SuccessTerminals(val terminals: Terminals) : LoadingState()

    class SuccessRegistration(val authorizationBodyResponse: AuthorizationBodyResponse): LoadingState()

    class SuccessLogin(val loginResponse: LoginResponse): LoadingState()

    class Error(val error: String?) : LoadingState()
}