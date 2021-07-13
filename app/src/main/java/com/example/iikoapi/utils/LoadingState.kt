package com.example.iikoapi.utils

import com.example.iikoapi.entities.menu.Menu
import com.example.iikoapi.entities.start.Terminals

sealed class LoadingState {
    object Loading : LoadingState()

    class SuccessMenu(val menu: Menu) : LoadingState()

    class SuccessTerminals(val terminals: Terminals) : LoadingState()

    class Error(val error: String?) : LoadingState()
}