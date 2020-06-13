package com.example.iikoapi.startapp.datatype

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PaymentItem(
    var sum: Int /*decimal Сумма к оплате */,
    var paymentType: PaymentType /*PaymentType Тип оплаты (одно из полей: id, code является обязательным)*/,
    var isProcessedExternally: Boolean /* bool Является ли позиция оплаты проведенной */,
    var isPreliminary: Boolean? /* bool Является ли позиция оплаты предварительной*/,
    var isExternal: Boolean? /*bool Принята ли позиция оплаты извне*/,
    var additionalData: String? /* string Дополнительная информация*/,
    var chequeAdditionalInfo: ChequeAdditionalInfo? /*ChequeAdditionalInfo Дополнительная информация о чеке оплаты*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ChequeAdditionalInfo(
    val needReceipt: Boolean /* bool Печатать ли бумажный чек */,
    val phone: String? /* string Телефон для отправки смс о чеке*/,
    val email: String? /* string Почта для отправки письма о чеке*/,
    val settlementPlace: String? /* string Место расчета*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class PaymentType(
    var id: String? /* Guid Идентификатор типа оплаты*/,
    var code: String? /* string Код типа оплаты*/,
    var name: String? /* string Название типа оплаты*/,
    var comment: String? /* string Комментарий к типу оплаты*/,
    var combinable: Boolean? /*bool Признак комбинируемости*/,
    var externalRevision: Int? /*long? Номер ревизии сущности из РМС*/,
    var deleted: Boolean? /*bool Признак, удален тип оплаты или нет*/,
    var applicableMarketingCampaigns: List<String>? /*Guid[] Массив маркетинговых акций, связанных с типом оплаты iikoCard5, применяемых для данной организации.*/
)