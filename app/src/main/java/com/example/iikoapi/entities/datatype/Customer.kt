package com.example.iikoapi.entities.datatype

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Customer(
    var id: String? /* Guid Идентификатор*/,
    var name: String? /* string Имя 60 */,
    var phone: String? /* string Телефонный номер. Регулярное выражение, которому должен соответствовать телефон. 40 **/,
    var cultureName: String? /* string Языковая культура пользователя, пример: RU-ru*/,
    var favouriteDish: String? /* string Любимое блюдо пользователя*/,
    var birthday: String? /* ShortDateTime День рождения*/,
    var email: String? /* string email 60*/,
    var nick: String? /* string Никнэйм 60*/,
    var middleName: String? /* string Отчество 60*/,
    var surName: String? /* string Фамилия 60*/,
    var shouldReceivePromoActionsInfo: Boolean? /* bool? Получает ли пользователь информацию о промоакциях*/,
    var sex: String? /* string Пол: NotSpecified = 0, Male = 1, Female = 2. Для входящих запросов передавать 0,1 или 2.*/,
    var imageId: String? /* Guid Идентификатор изображения пользователя*/,
    var customProperties: String? /* {“Key”:””,” Value”:””}[]массив key-value значений дополнительных свойств*/,
    var publicCustomProperties: String? /* {“Key”:””,” Value”:””}[] массив key-value значений публичных дополнительных свойств*/,
    var balance: Int? /* decimal Баланс пользователя*/,
    var isBlocked: Boolean? /* bool Заблокирован ли пользователь в организации*/,
    var additionalPhones: List<CustomerPhone>? /*CustomerPhone[] Дополнительные телефоны*/,
    var addresses: List<Address>? /*CustomerAddress[] Адреса*/,
    var cards: List<GuestCardInfo>? /*GuestCardInfo[] Карты*/
){
    constructor():this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
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
    fun setThisName(value:String){
        name=value
    }
    fun setThisPhone(value: String){
        phone=value
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
data class CustomerPhone(
    var phone: String /*string Номер телефон*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class GuestCardInfo(
    var Id: String /* string Идентификатор карты */,
    var Track: String /* string Трек карты */,
    var Number: String? /* string Номер карты*/,
    var IsActivated: Boolean /* bool Признак, активирована карта или нет */,
    var OrganizationId: String /* Guid Идентификатор организации, в которой действует карта*/
)