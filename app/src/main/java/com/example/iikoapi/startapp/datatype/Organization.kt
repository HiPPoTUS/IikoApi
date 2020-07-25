package com.example.iikoapi.startapp.datatype
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

@JsonIgnoreProperties(ignoreUnknown = true)
//data class OrganisationInfo(
//    var id: String /*Guid Идентификатор организации*/,
//    var name: String /* string Название организации(не юр. лицо)*/,
//    var description: String? /* string Описание организации данное владельцем*/,
//    var logo: String? /* string Ссылка на изображение с логотипом организации.*/,
//    var contact: ContactInfo? /*Контактная информация в свободной форме.*/,
//    var homePage: String? /* string Домашняя страница*/,
//    var address: String? /* string Адрес*/,
//    var isActive: Boolean?/* Активна*/,
//    var longitude: Int? /*Географическая долгота*/,
//    var latitude: Int? /*Географическая широта*/,
//    var networkId: String? /* Guid? Идентификатор сети, если организация входит в сеть*/,
//    var logoImage: String? /* string Логотип организации, если есть*/,
//    var phone: String? /* Номер телефона*/,
//    var website: String? /* string Веб сайт*/,
//    var averageCheque: String? /* string Средний чек*/,
//    var workTime: String? /* string время работы, представляет собой строку состоящую из записей начало работы-окончание работы*/,
//    var bizOrganizationType: Int? /*Тип организации*/,
//    var currencyIsoName: String? /* string Код валюты, используемой в организации*/
//)

data class OrganisationInfo (
    val address: String? = null,
    val averageCheque: Any? = null,
    val contact: Contact? = null,

    @get:JsonProperty("currencyIsoName")@field:JsonProperty("currencyIsoName")
    val currencyISOName: String? = null,

    val description: String? = null,
    val fullName: String? = null,
    val homePage: Any? = null,
    val id: String? = null,

    @get:JsonProperty("isActive")@field:JsonProperty("isActive")
    val isActive: Boolean? = null,

    val latitude: Long? = null,
    val logo: Any? = null,
    val logoImage: Any? = null,
    val longitude: Long? = null,
    val maxBonus: Long? = null,
    val minBonus: Long? = null,
    val name: String? = null,

    @get:JsonProperty("networkId")@field:JsonProperty("networkId")
    val networkID: String? = null,

    val organizationType: Long? = null,
    val phone: Any? = null,
    val timezone: String? = null,
    val website: Any? = null,
    val workTime: Any? = null
)

data class Contact (
    val email: String? = null,
    val location: String? = null,
    val phone: Any? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class DeliveryTerminalInfo(
    var deliveryTerminalId: String /* Guid Идентификатор доставочного терминала */,
    var crmId: String /* String CrmId ресторана, к которому относится доставочный терминал*/,
    var restaurantName: String /* String Наименование доставочного терминала */,
    var externalRevision: Int? /* long Номер ревизии сущности из РМС*/,
    var technicalInformation: String? /* String Техническая информация*/,
    var address: String? /* String Адрес ресторана*/,
    var protocolVersion: Int? /* int Версия протокола 0 для старых версий рмс, 1 и выше для версий от 7.1.2 и старше (поддержка в api с версии 7.1.5)*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class DeliveryTerminal(
    var organizationId: String /* Guid Идентификатор организации, к которой относится доставочный терминал*/,
    var deliveryRestaurantName: String /* string Наименование доставочного терминала */,
    var deliveryTerminalId: String /* Guid Идентификатор доставочного терминала */,
    var name: String? /* string Имя ресторана*/,
    var address: String? /* string Адрес ресторана*/,
    var workTime: List<OpeningHours>? /*OpeningHours[] Время работы ресторана*/,
    var externalRevision: Int? /* long? Номер ревизии сущности из РМС*/,
    var technicalInformation: String? /* string Техническая информация.*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class OpeningHours(
    var dayOfWeek: Int /* int Номер для недели, для которого указывается время работы. Нумерация начинается c 0, которому соответствует понедельник*/,
    var from: String? /* string Время, с которого работает заведение. Строка в формате “hh:mm”*/,
    var to: String? /* string Время, до которого работает заведение. Строка в формате “hh:mm”*/,
    var allDay: Boolean /* bool Флаг отображающий, что заведение работает 24 часа */,
    var closed: Boolean /* bool Флаг отображающий, что заведение не работает в этот день*/
)

data class CustomTerminalPriceInfo(
    val terminalId: String /*Guid Идентификатор терминала, на котором цена отличается от стандартной*/,
    val price: Int /*decimal Цена на терминале*/,
    val priceCategory: String? /* Guid? Идентификатор ценовой категории терминала*/
)