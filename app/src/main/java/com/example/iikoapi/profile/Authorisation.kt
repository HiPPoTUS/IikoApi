package com.example.iikoapi.profile

import java.io.Serializable

sealed class Authorisation: Serializable {
    object Login: Authorisation()
    object Registration: Authorisation()
}