package com.example.iikoapi.entities.customer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Customer(
    var id: String? /* Guid Идентификатор*/,
    var name: String? /* string Имя 60 */,
    var phone: String? /* string Телефонный номер. Регулярное выражение, которому должен соответствовать телефон. 40 **/,
//    var cultureName: String? /* string Языковая культура пользователя, пример: RU-ru*/,
    var favouriteDish: String? /* string Любимое блюдо пользователя*/,
    var birthday: String? /* ShortDateTime День рождения*/,
    var email: String? /* string email 60*/,
    var shouldReceivePromoActionsInfo: Boolean? /* bool? Получает ли пользователь информацию о промоакциях*/,
    var sex: String? /* string Пол: NotSpecified = 0, Male = 1, Female = 2. Для входящих запросов передавать 0,1 или 2.*/,
    var imageId: String? /* Guid Идентификатор изображения пользователя*/,
)