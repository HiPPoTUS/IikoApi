package com.example.iikoapi.entities.menu

data class GroupModifier(
    val id: String,
    val name: String,
    val groupId: String,
    val parentGroup: String,
    val maxAmount: Long? = null,
    val minAmount: Long? = null,
    val modifierId: String? = null,
    val required: Boolean? = null,
    val defaultAmount: Long? = null,
    val hideIfDefaultAmount: Boolean? = null,
    val price: String
){
    fun getType() = when(this.name){
        "---БЕЗ---" -> ModifiersTypes.BEZ
        "-лаваши с питой" -> ModifiersTypes.HLEB_WITH_PITA
        "-лаваши без питы" -> ModifiersTypes.HLEB_WITHOUT_PITA

        else -> ModifiersTypes.DOBAVIT
    }
}