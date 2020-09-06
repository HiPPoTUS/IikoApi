package com.example.iikoapi.startapp.networking

import Group
import Product
import ProductCategory
import com.example.iikoapi.startapp.datatype.*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class MenuResponse (
    val groups: List<Group>? = null,
    val productCategories: List<ProductCategory>? = null,
    val products: List<Product>? = null,
    val revision: Long? = null,
    val uploadDate: String? = null
){
    fun getGroups(isIncluded: Boolean = true):List<Group>{
        return groups!!.filter { it.isIncludedInMenu == isIncluded }.sortedBy { it.order }
    }
    fun getGroupProds(group:Group, isModifier:Boolean=false):List<Product>{
        return if (!isModifier) products!!.filter { it.parentGroup==group.id}.sortedBy { it.order }
        else products!!.filter { it.groupID==group.id}.sortedBy { it.order }
    }
    fun getGroupsProds(groups:List<Group>, isModifier: Boolean=false):MutableMap<String,List<Product>>
    {
        val map = mutableMapOf<String,List<Product>>()
        groups.forEach { map[it.id!!] = getGroupProds(it,isModifier) }
        return map
    }

//    fun getByType(type: String):List<Product>{
//        try {
//            return products.groupBy { it.type }[type]!!.toList()
//        }
//        catch (e:Exception){
//            Log.e("invalid type","only: dish, modifier, good")
//            return emptyList<Product>()
//        }
//    }
//    fun groupAndProducts(type: String):Map<Group?,List<Product>>{
//        val tmp = getByType(type)
//        val ids:Map<String?,List<Product>>
//        if (type=="dish")
//            ids= tmp.groupBy { it.parentGroup }
//        else ids = tmp.groupBy { it.groupId }
//        return ids.mapKeys { groups.find {group -> group.id == it.key} }
//    }
//    fun getModifiers(product: Product, groupNameID: List<String?>):List<Product>{
//        if(groupNameID[0].isNullOrEmpty()){
//            if (product.modifiers.isEmpty())
//                return emptyList()
//            else{
//                return List(product.modifiers.size){
//                    groupAndProducts("modifier")[groups.find { it.name==groupNameID[0]}]!!.find {prod ->
//                        prod.id == product.modifiers[it].modifierId
//                    }!!
//                }
//            }
//        }
//        else{
//            if (product.groupModifiers.isEmpty())
//                return emptyList()
//            else{
//                val tmp = product.groupModifiers.find {
//                    it.modifierId==groupNameID[1]
//                }
//                if (tmp==null)
//                    return emptyList()
//                else return groupAndProducts("modifier")[groups.find { it.id==tmp.modifierId}]!!
//            }
//        }
//    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class DeliveryRestrictionsResponse(
    var deliveryRegionsMapUrl: String? /*Ссылка на карту регионов обслуживания доставки*/,
    var defaultDeliveryDurationInMinutes: Int? /*Общая продолжительность доставки*/,
    var useSameDeliveryDuration: Boolean? /*bool Признак того, что ресторан(ы) используют общие ограничения по времени доставки**/,
    var useSameMinSum: Boolean? /*bool Признак того, что ресторан(ы) по всем зонам используют одинаковую минимальную сумму**/,
    var defaultMinSum: Int? /*decimal Общая минимальная сумма заказа*/,
    var useSameWorkTimeInterval: Boolean? /*bool Признак того что ресторан(ы) использует общий интервал работы для всех зон.**/,
    var defaultFrom: Int? /*Начало интервала по умолчанию работы ресторана, в минутах от начала дня. Используется совместно с useSameWorkTimeInterval*/,
    var defaultTo: Int? /*Конец интервала по умолчанию работы ресторана, в минутах от начала дня. Если defaultTo < defaultFrom, то это означает, что конец рабочего дня залезает на следующий день. Используется совместно с useSameWorkTimeInterval*/,
    var useSameRestructionsOnAllWeek: Boolean? /*bool Признак того, что ограничения работы точек распространяются на все дни недели.**/,
    var restrictions: List<DeliveryRestrictionItem>? /*DeliveryRestrictionItem[] Привязки ресторанов к зонам доставки*/,
    var deliveryZones: List<DeliveryZone>? /*DeliveryZone[] Список доставочных зон из Яндекс.Карт*/
)

@JsonIgnoreProperties(ignoreUnknown = true)
 data class AddressCheckResult(
     var addressInZone: Boolean
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderChecksCreationResult(
    var deliveryRestriction: DeliveryRestrictionItem, //Сработавшее ограничение (в случае, если удалось найти точку, которая может обработать заказ)
    var problem: String, //Описание ошибки (в случае, если доставка не может быть обработана рестораном)
    var resultState: Int, /*Результат проверки:
Success = 0 (Доставка была успешна распределена),
RejectByMinSum = 1 (Доставка отвергнута по
минимальной сумме заказа),
RejectByWorkTime = 2 (Доставка отвергнута по
времени работы заведения),
RejectByZone = 3 (Доставка отвергнута по причине
отсутсвия подходящей зоны. Возможные причины:
адрес не отгеокодирован, адрес не входит не в одну из
зон, адрес не был найден (в RMS)),
RejectByStopList = 4 (Продукт из заказа находятся в
стоп-листе),
RejectByPriceList = 5. (Продукт из заказа запрещен к
продаже)*/
    var deliveryServiceProductInfo: DeliveryServiceProductInfo //Дополнительная плата за доставку.
)