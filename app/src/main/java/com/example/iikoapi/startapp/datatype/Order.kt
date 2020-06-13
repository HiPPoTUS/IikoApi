package com.example.iikoapi.startapp.datatype
import android.util.Log
import com.example.dodocopy.dataTypes.Address
import com.example.dodocopy.dataTypes.Customer
import com.example.iikoapi.openedmenuitem.order
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.example.iikoapi.startapp.networking.MenuResponse
import java.lang.Exception

@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderRequest (
    var organization: String? /*string Идентификатор ресторана, список доступных ресторанов*/,
    var deliveryTerminalId: String? /* Guid Идентификатор доставочного термина, на который нужно отправить заказ.*/,
    var customer: Customer? /*Customer Заказчик*/,
    var order: Order? /*Order Заказ*/,
    var coupon : String? /* Номер купона, который применяется к заказу.*/,
    var availablePaymentMarketingCampaignIds: List<String>? /*Guid[] Массив идентификаторов применяемых акций, содержащих Действия оплаты. Если действия оплаты не используются, то массив должен быть пустым.*/,
    var applicableManualConditions: List<String>? /* Guid[] Массив идентификаторов ручных условий, которые применяются к заказу.*/,
    var customData: String? /*string Служебная информация. Только хранится, доступна через API, на UI не выводится*/,
    var emailForFailedOrderInfo: String? /*string Email для отправки информации о заказе при проблемах с созданием.*/,
    var referrerId: String?){
    constructor():this(
        null,  //should be presetted
        null,
        Customer(),
        Order(),
        null,
        null,
        null,
        null,
        null,
        null)
    fun setOrganisation(value:String){
        organization=value
    }
    fun setThisCustomer(value: Customer){
        customer=value
    }
    fun setThisOrder(value: Order){
        order=value
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
data class Order(
    var id: String? /* Guid Идентификатор заказа*/,
    var externalId: String? /* string Идентификатор заказа – должен быть уникальным в рамках данной организации*/,
    var date: String? /*DateTime Дата выполнения заказа, если задан null, тосистема подставит время как текущее + продолжительность доставки из “График работы и картография”*/,
    var items: MutableList<OrderItem> /*OrderItem[] Элементы заказа*/,
    var paymentItems: List<PaymentItem>? /*PaymentItem[] Элементы оплаты заказа*/,
    var phone: String? /* string Контактный телефон. Регулярное выражение, которому должен соответствовать телефон: ^(8|\+?\d{1,3})?[ -]?\(?(\d{3})\)?[ -]?(\d{3})[-]?(\d{2})[ -]?(\d{2})$40 */,
    var customerName: String? /* string Имя клиента 60*/,
    var isSelfService: Boolean? /*bool Признак доставки самовывозом*/,
    var orderTypeId: String? /* Guid Идентификатор типа заказа. Получается методом Получение списка допустимых типов заказов*/,
    var address: Address? /*Address Адрес доставки заказа*/,
    var comment: String? /* string Комментарий к заказу 500*/,
    var conception: String? /* string Концепция*/,
    var personsCount: Int? /*int Количество персон*/,
    var fullSum: Int /*Decimal Сумма заказа */,
    var marketingSource: String? /* string Маркетинговый источник (реклама). Можно указывать не более одного источника. Пример: deliveryMarket.ru*/,
    var marketingSourceId: String? /* Guid Идентификатор маркетингового источника*/,
    var discountCardTypeId: String? /* Guid Идентификатор скидки для заказа. Получается методом Получить список скидок, доступных для применения в доставке для заданного ресторана*/,
    var discountCardSlip: String? /* string Трек скидочной карты, которую надо применить к заказу. Если указан одновременно с discountCardTypeId, то будет применятся скидка по discountCardTypeId.*/,
    var discountOrIncreaseSum: Int? /*decimal Сумма скидки. Необходима только для скидок со свободной суммой.*/,
    var orderCombos: List<DeliveryOrderCombo>? /*DeliveryOrderCombo[] Массив комбо-блюд, включенных в заказ.*/,
    var totalItems: Int /*ATTENTION! DO NOT SERIALIZE THIS*/
){
    constructor():this(
        null,
        null,
        null,
        mutableListOf(),
        null,
        null,
        null,
        null,//should be presetted
        null, //should be presetted
        Address(),
        null,
        null,
        null,
        0,
        null,
        null,
        null,
        null,
        null,
        null,
        0
    )
    fun setThisItems(value:MutableList<OrderItem>){
        items=value
    }
    fun setThisAddress(value:Address){
        address=value
    }
    fun setThisPayments(value:List<PaymentItem>){
        paymentItems=value
    }
    fun setThisFullSum(value:Int){
        fullSum=value
    }
    fun setThisOrderTypeId(value:String){
        orderTypeId=value
    }
    fun addToOrder(item: OrderItem){
        if (items.isEmpty()){
            items.add(item)}
        else{
            var check:Boolean = true
            for (ab in items)
            {
                if(ab.equals(item)){
                    ab.amount += 1
                    check = false
                    break
                }
            }
            if (check) items.add(item)
        }
    }
    fun update(){
        fullSum=0
        totalItems=0
        items.forEach {
            totalItems+=it.amount
            fullSum+=it.sum
        }
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderTypeInfo(
    var Id: String /* Guid Идентификатор типа заказа */,
    var Name: String /* String Наименование тапа заказа */,
    var OrderServiceType: String /* String Сервисный тип заказа */,
    var externalRevision: Int /* long? Номер ревизии сущности из РМС*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderItem(
    var id: String? /* Guid Идентификатор продукта*/,
    var code: String? /* string Артикул товара */,
    var name: String? /* string Название продукта*/,
    var amount: Int /*decimal Количество <1000 */,
    var sum: Int /*decimal Стоимость Обязательно при расчете скидок и бонусов*/,
    var category: String? /* string Категория товара Обязательно при расчете скидок и бонусов*/,
    var modifiers: MutableList<OrderItemModifier> /*OrderItemModifier[] Модификаторы*/,
    var comment: String? /* string Комментарий 255*/,
    var guestId: String? /* Guid Идентификатор гостя*/,
    var comboInformation: ComboItemInformation? /*ComboItemInformation Информация о комбо-блюде, если позиция в заказе относится к комбо.*/,
    var imageUrl: String? /*ATTENTION! DO NOT SERIALIZE THIS*/,
    var info: String? /*ATTENTION! DO NOT SERIALIZE THIS*/,
    var modifSum: Int,
    var saveSum: Int
){
    constructor():this(
        null,
        null,
        null,
        1,
        0,
        null,
        mutableListOf(),
        null,
        null,
        null,
        null,
        null,
        0,
        0
    )

    fun fromProduct(product: Product):OrderItem{
        id = product.id
        name = product.name
        code = product.code
        saveSum = product.price
        amount = 1
        imageUrl = product.images.getOrNull(0)?.imageUrl
        info = product.seoDescription
        return this
    }
    fun equals(other: OrderItem):Boolean
    {
        return (this.id==other.id&&this.name==other.name&&this.modifiers==other.modifiers)
    }
    fun update(){
        modifSum = 0
        modifiers.forEach {
            modifSum += it.price*it.amount
        }
        sum = saveSum*amount+modifSum
    }

}
@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderItemModifier(
    var id: String? /* Guid Идентификатор продукта */,
    var name: String? /* string Название продукта */,
    var amount: Int /*decimal Количество */,
    var groupId: String? /* Guid Идентификатор группы в случае группового модификатора. Обязателен если модификатор является групповым.*/,
    var groupName: String? /* string Имя группы в случае группового модификатора. Обязателен если модификатор является групповым.*/,
    var price: Int /*ATTENTION! DO NOT SERIALIZE THIS*/
) {
    constructor() : this(
        null,
        null,
        0,
        null,
        null,
        0
    )

    fun fromProduct(product: Product, groupName: List<String?>):OrderItemModifier{
        this.id = product.id
        this.name = product.name
        this.price = product.price
        this.amount = 0
        this.groupId = groupName[1]
        this.groupName = groupName[0]
        return this
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
data class DeliveryOrderCombo(
    var id: String /* Guid Идентификатор комбо-блюда. Произвольный. Должен совпадать с order.items[].comboInformation.id*/,
    var name: String /* string Имя комбо-блюда */,
    var amount: Int /*int Количество комбо-блюд */,
    var price: Int /*decimal Стоимость 1 единицы комбо-блюда. Без учета amount. */,
    var sourceId: String /* Guid Идентификатор действия, которое порождает данное комбо. Использовать значение из ComboItemInformation.ComboSource.Id*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ComboItemInformation(
    var comboId: String /* Guid Идентификатор созданного комбо-блюда, к которой относится данная позиция заказа.(DeliveryOrderCombo.id)*/,
    var comboSourceId: String /* Guid Идентификатор действия, которое задает комбо. (DeliveryOrderCombo.sourceId). Можно узнать из метода получения спецификаций комбо. */,
    var groupId: String /* Guid Идентификатор группы комбо, к которой относится данная позиция заказа. Можно узнать из метода получения спецификаций комбо.*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderInfo(
    var orderId: String /*  Guid Идентификатор заказа */,
    var customerId: String? /*  Guid Идентификатор заказчика*/,
    var customer: Customer? /*Customer Клиент доставки*/,
    var address: Address? /*Address Адрес доставки*/,
    var organization: String? /*  Guid Идентификатор ресторана*/,
    var sum: Int? /*decimal Сумма заказа*/,
    var discount: Int? /* decimal Сумма скидки*/,
    var number: String? /*  string Понятный номер заказа. Может использоваться для передачи клиенту. Уникальность не гарантирована (может быть уникальным в рамках одного обслуживающего сервера).*/,
    var status: String? /*  string Статус заказа. Варианты значения для доставки*/,
    var customerName: String? /*  string Имя клиента*/,
    var deliveryCancelCause: String? /*DeliveryCancelCauseInfo Причина отмены доставки*/,
    var deliveryCancelComment: String? /*  string Комментарий к отмене доставки*/,
    var courierInfo: String? /*OrderCourierInfo Информация о курьере заказа*/,
    var orderLocationInfon: String? /*CoordinatesInfo Координаты адреса доставочного заказа*/,
    var deliveryDate: String? /*  DateTime Дата, к которой нужно доставить заказ*/,
    var actualTime: String? /*  DateTime Фактическое время доставки*/,
    var billTime: String? /*  DateTime Время печати накладной (время пречека)*/,
    var cancelTime: String? /*  DateTime Время отмены доставки*/,
    var closeTime: String? /*  DateTime Время закрытия доставки*/,
    var confirmTime: String? /*  DateTime Время подтверждения доставки*/,
    var createdTime: String? /*  DateTime Время создания доставки*/,
    var printTime: String? /*  DateTime Время сервисной печати*/,
    var sendTime: String? /*  DateTime Время отправки доставки*/,
    var comment: String? /*  string Комментарий к заказу*/,
    var problem: String? /*DeliveryProblemInfo Проблема доставки*/,
    var operator: String? /*OrganizationUser Оператор, принявший заказ*/,
    var conception: String? /*ConceptionInfo Концепция*/,
    var marketingSource: String? /*MarketingSourceInfo Маркетинговый источник*/,
    var durationInMinutes: Int? /* int Продолжительность доставки (в минутах)*/,
    var personsCount: Int? /* int Количество гостей*/,
    var splitBetweenPersons: Boolean? /*bool Признак того, нужно ли разбивать заказ по гостям*/,
    var iikoCard5Coupon: String? /* string Примененный к заказу купон*/,
    var items: List<OrderItem>? /*OrderItem[] Позиции заказа*/,
    var guests: List<DeliveryOrderGuest>? /*DeliveryOrderGuest[] Гости заказа*/,
    var payments: List<PaymentItem>? /*PaymentItem[] Оплаты доставки*/,
    var orderType: OrderTypeInfo? /*OrderTypeInfo Тип заказа*/,
    var deliveryTerminal: DeliveryTerminalInfo? /*DeliveryTerminalInfo Доставочный терминал*/,
    var discounts: List<DiscountInfo>? /*DiscountInfo[] Скидки*/,
    var customData: String? /* string Служебная информация. Только хранится, доступна через API, на UI не выводится*/,
    var opinion: DeliveryOpinion? /*DeliveryOpinion Отзывы клиента о заказе*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class DiscountInfo(
    var discountCardTypeId: String /* Guid Идентификатор скидки */,
    var discountCardSlip: String? /* String Трек скидочной карты*/,
    var discountOrIncreaseSum: Int /* decimal Сумма скидки*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class DeliveryOrderGuest(
    var id: String /*Guid Идентификатор гостя */,
    var name: String /*string Имя гостя*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class DeliveryOpinion(
    var organization: String /* Guid Идентификатор организации */,
    var deliveryId: String /* Guid Идентификатор доставочного заказа */,
    var comment: String? /* string Текстовый отзыв клиента о доставке*/,
    var marks: List<DeliveryOpinionMark>? /*DeliveryOpinionMark[] Оценки клиента*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class DeliveryOpinionMark(
    var surveyItemId: String /*Guid ID вопроса */,
    var mark: Int /*int Оценка */
)



