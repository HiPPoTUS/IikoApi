package com.example.iikoapi.startapp.datatype

import com.fasterxml.jackson.annotation.JsonProperty

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
            : String? = null,
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
)