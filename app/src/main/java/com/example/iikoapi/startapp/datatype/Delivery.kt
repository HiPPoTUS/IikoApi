package com.example.iikoapi.startapp.datatype

data class DeliveryRestrictionItem(
    var organizationId: String,// Guid Ресторан, к которому применяется заданное ограничение*,
    var deliveryTerminalId: String,// Guid Доставочный терминал, к которому применяется заданное ограничение. Null, если доставка одноресторанная,
    var minSum: Int,// decimal Минимальная сумма заказа,
    var zone: String,// string Наименование зоны доставки,
    var weekMap: Int,// int Битовый массив, описывающий дни недели, к которым применяется заданное ограничение*,
    var from: Int,// int Начало интервала работы ресторана, в минутах от начала дня,
    var to: Int,// int Конец интервала работы ресторана, в минутах от начала дня. Если to < from, то это означает, что конец рабочего дня залезает на следующий день.,
    var priority: Int,// int Приоритет ресторана над зоной доставки*,
    var deliveryDurationInMinutes: Int// int Время доставки ресторана в указанную зону *,
)

data class DeliveryZone(
    var name: String,// string Наименование зоны доставки *,
    var coordinates: List<CoordinatesInfo>// CoordinatesInfo[] Координаты вершин многоугольника *,
)

data class CoordinatesInfo(
    var latitude: Int,// decimal Широта *
    var longitude: Int// decimal Долгота *
)