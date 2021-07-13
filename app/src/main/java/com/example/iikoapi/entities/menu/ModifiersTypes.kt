package com.example.iikoapi.entities.menu

sealed class ModifiersTypes{
    object BEZ: ModifiersTypes()
    object HLEB_WITH_PITA: ModifiersTypes()
    object HLEB_WITHOUT_PITA: ModifiersTypes()
    object DOBAVIT: ModifiersTypes()
}