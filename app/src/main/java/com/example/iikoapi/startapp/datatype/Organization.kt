package com.example.iikoapi.startapp.datatype

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName

data class Login (
    @SerializedName("user_id")
    var user_id: String,
    @SerializedName("user_secret")
    var user_secret: String,
    var access: String?
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class OrganisationInfo(
    var id: String /*Guid Идентификатор организации*/,
    var name: String /* string Название организации(не юр. лицо)*/,
    var description: String? /* string Описание организации данное владельцем*/,
    var logo: String? /* string Ссылка на изображение с логотипом организации.*/,
    var contact: ContactInfo? /*Контактная информация в свободной форме.*/,
    var homePage: String? /* string Домашняя страница*/,
    var address: String? /* string Адрес*/,
    var isActive: Boolean?/* Активна*/,
    var longitude: Int? /*Географическая долгота*/,
    var latitude: Int? /*Географическая широта*/,
    var networkId: String? /* Guid? Идентификатор сети, если организация входит в сеть*/,
    var logoImage: String? /* string Логотип организации, если есть*/,
    var phone: String? /* Номер телефона*/,
    var website: String? /* string Веб сайт*/,
    var averageCheque: String? /* string Средний чек*/,
    var workTime: String? /* string время работы, представляет собой строку состоящую из записей начало работы-окончание работы*/,
    var bizOrganizationType: Int? /*Тип организации*/,
    var currencyIsoName: String? /* string Код валюты, используемой в организации*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ContactInfo(
    var phone: String? /*string Телефон*/,
    var location: String? /*string Адрес*/,
    var email: String? /*string Адрес электронной почты*/
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