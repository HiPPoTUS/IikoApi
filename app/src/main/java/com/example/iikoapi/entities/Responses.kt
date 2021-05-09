package com.example.iikoapi.entities

import com.example.iikoapi.entities.datatype.*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MenuResponse (
    val groups: List<Group>? = null,
    val productCategories: List<ProductCategory>? = null,
    val products: List<Product>? = null,
    val revision: Long? = null,
    val uploadDate: String? = null
){
    fun get_groups(isIncluded: Boolean = true):List<Group>{
        return groups!!.filter { it.isIncludedInMenu == isIncluded }.sortedBy { it.order }
    }
    fun get_group_prods(group: Group, isModifier:Boolean=false):List<Product>{
        if (!isModifier) return products!!.filter { it.parentGroup==group.id}.sortedBy { it.order }
        else return products!!.filter { it.groupID==group.id}.sortedBy { it.order }
    }
    fun get_groups_prods(groups:List<Group>, isModifier: Boolean=false):MutableMap<String,List<Product>>
    {
        val map = mutableMapOf<String,List<Product>>()
        groups.forEach { map.put(it.id!!, get_group_prods(it,isModifier)) }
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
data class paymentTypesResponse(
    val paymentTypes : ArrayList<PaymentType>
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class DeliveryRestrictionsResponse (
    @get:JsonProperty("deliveryRegionsMapUrl")@field:JsonProperty("deliveryRegionsMapUrl")
    val deliveryRegionsMapURL: String? = null,

    val defaultDeliveryDurationInMinutes: Long? = null,
    val defaultSelfServiceDurationInMinutes: Long? = null,
    val useSameDeliveryDuration: Boolean? = null,
    val useSameMinSum: Boolean? = null,
    val defaultMinSum: Long? = null,
    val useSameWorkTimeInterval: Boolean? = null,
    val defaultFrom: Any? = null,
    val defaultTo: Any? = null,
    val useSameRestrictionsOnAllWeek: Boolean? = null,
    val restrictions: List<Restriction>? = null,
    val deliveryZones: List<DeliveryZone>? = null
)

data class DeliveryZone (
    val name: String? = null,
    val coordinates: List<Coordinate>? = null
)

data class Coordinate (
    val latitude: Double? = null,
    val longitude: Double? = null
)

data class Restriction (
    val minSum: Long? = null,

    @get:JsonProperty("deliveryTerminalId")@field:JsonProperty("deliveryTerminalId")
    val deliveryTerminalID: String? = null,

    @get:JsonProperty("organizationId")@field:JsonProperty("organizationId")
    val organizationID: String? = null,

    val zone: String? = null,
    val weekMap: Long? = null,
    val from: Any? = null,
    val to: Any? = null,
    val priority: Long? = null,
    val deliveryDurationInMinutes: Long? = null
)

data class AddressCheckResult (
    val addressInZone: Boolean? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderChecksCreationResult(
    var deliveryRestriction: DeliveryRestrictionItem?, //Сработавшее ограничение (в случае, если удалось найти точку, которая может обработать заказ)
    var problem: String?, //Описание ошибки (в случае, если доставка не может быть обработана рестораном)
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
    var deliveryDurationInMinutes: Int,
    var deliveryServiceProductInfo: DeliveryServiceProductInfo? //Дополнительная плата за доставку.
)