package com.example.dodocopy.dataTypes

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Address(
    var city: String? /* string Наименование города 255 */,
    var street: String? /* string Наименование улицы 255 **/,
    var streetId: String? /* Guid Идентификатор улицы (если справочник улиц синхронизирован с справочником улиц в RMS)*/,
    var streetClassifierId: String? /* string Идентификатор улицы в классификаторе, например, КЛАДР. 255*/,
    var home: String? /* string Дом 10 **/,
    var housing: String? /* string Корпус 10*/,
    var apartment: String? /* string Квартира 10*/,
    var entrance: String? /* string Подъезд 10*/,
    var floor: String? /* string Этаж 10*/,
    var doorphone: String? /* string Домофон 10*/,
    var comment: String? /* string Дополнительная информация 500*/,
    var regionId: String? /* Guid Идентификатор района, к которому относится адрес*/,
    var externalCartographyId: String? /* string Идентификатор адреса во внешней картографической системе 255*/,
    var index: String? /* string Индекс улицы в адресе, если есть 255*/
){
    constructor(city:String,street: String,home: String):this(
        city,
        street,
        null,
        null,
        home,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
    fun setThisCity(value:String){
        city=value
    }
    fun setThisStreet(value:String){
        street=value
    }
    fun setThisHome(value:String){
        home=value
    }
    fun setThisHousing(value:String){
        housing=value
    }
    fun setThisApartment(value:String){
        apartment=value
    }
    fun setThisEntrance(value:String){
        entrance=value
    }
    fun setThisFloor(value:String){
        floor=value
    }
    fun setThisDoorphone(value:String){
        doorphone=value
    }
    fun setThisComment(value:String){
        comment=value
    }
}
data class CityWithStreets (
    val city: City? = null,
    val streets: List<City>? = null
)

data class City (
    val additionalInfo: Any? = null,

    @get:JsonProperty("classifierId")@field:JsonProperty("classifierId")
    val classifierID: String? = null,

    val deleted: Boolean? = null,
    val externalRevision: Long? = null,
    val id: String? = null,
    val name: String? = null,

    @get:JsonProperty("cityId")@field:JsonProperty("cityId")
    val cityID: String? = null
)