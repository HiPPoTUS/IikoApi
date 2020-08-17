package com.example.iikoapi.startapp.datatype

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
data class Transaction (
    @JsonProperty("TransactionId")
    var id: String? = null,
    @JsonProperty("ReasonCode")
    var reasonCode: Int = 0,
    @JsonProperty("CardHolderMessage")
    var cardHolderMessage: String? = null,
    // 3DS Begin
    @JsonProperty("PaReq")
    var paReq: String? = null,
    @JsonProperty("AcsUrl")
    var acsUrl: String? = null
)

data class PayRequestArgs (
    @JsonProperty("amount")
    var amount // Сумма платежа (Обязательный)
            : Int? = null,
    @JsonProperty("currency")
    var currency // Валюта (Обязательный)
            : String? = null,
    @JsonProperty("name")
    var name // Имя держателя карты в латинице (Обязательный для всех платежей кроме Apple Pay и Google Pay)
            : String? = null,
    @JsonProperty("card_cryptogram_packet")
    var cardCryptogramPacket // Криптограмма платежных данных (Обязательный)
            : String? = null,
    @JsonProperty("invoice_id")
    var invoiceId // Номер счета или заказа в вашей системе (необязательный)
            : String? = null,
    @JsonProperty("description")
    var description // Описание оплаты в свободной форме (необязательный)
            : String? = null,
    @JsonProperty("account_id")
    var accountId // Идентификатор пользователя в вашей системе (необязательный)
            : String? = null,
    @JsonProperty("json_data")
    var jsonData // Любые другие данные, которые будут связаны с транзакцией (необязательный)
            : String? = null
){
    constructor(sum:Int, name:String, crypt:String) : this(
        sum,
        "RUB",
        name,
        crypt,
        null,
        "Заказ",
        null,
        null
    )
}
@JsonIgnoreProperties(ignoreUnknown = true)
data class PayApiResponse<T> (
    @JsonProperty("Success")
    var success:Boolean,
    @JsonProperty("Message")
    var message:String,
    @JsonProperty("Model")
    var data:T?
) {
    fun isSuccess():Boolean
    {
        if (success == false && data == null)
            return false;
        else if (success == false && data != null)
            return true;
        else
            return success;
    }
}

data class Post3dsRequestArgs (
    @JsonProperty("transaction_id")
    var transactionId: String? = null,
    @JsonProperty("pa_res")
    var paRes: String? = null

)