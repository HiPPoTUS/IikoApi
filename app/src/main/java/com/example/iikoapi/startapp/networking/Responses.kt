package com.example.iikoapi.startapp.networking

import android.util.Log
import com.example.iikoapi.startapp.datatype.*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName
import java.lang.Exception

@JsonIgnoreProperties(ignoreUnknown = true)
data class MenuResponse(
    @SerializedName("groups")
    var groups: List<Group>,
    @SerializedName("products")
    var products: List<Product>,
    @SerializedName("revision")
    var revision: Int,
//    var productCategories: List<ProductCategory>,
    var uploadDate: String
){
    fun getByType(type: String):List<Product>{
        try {
            return products.groupBy { it.type }[type]!!.toList()
        }
        catch (e:Exception){
            Log.e("invalid type","only: dish, modifier, good")
            return emptyList<Product>()
        }
    }
    fun groupAndProducts(type: String):Map<Group?,List<Product>>{
        val tmp = getByType(type)
        val ids:Map<String?,List<Product>>
        if (type=="dish")
            ids= tmp.groupBy { it.parentGroup }
        else ids = tmp.groupBy { it.groupId }
        return ids.mapKeys { groups.find {group -> group.id == it.key} }
    }
    fun getModifiers(product: Product, groupNameID: List<String?>):List<Product>{
        if(groupNameID[0].isNullOrEmpty()){
            if (product.modifiers.isEmpty())
                return emptyList()
            else{
                return List(product.modifiers.size){
                    groupAndProducts("modifier")[groups.find { it.name==groupNameID[0]}]!!.find {prod ->
                        prod.id == product.modifiers[it].modifierId
                    }!!
                }
            }
        }
        else{
            if (product.groupModifiers.isEmpty())
                return emptyList()
            else{
                val tmp = product.groupModifiers.find {
                    it.modifierId==groupNameID[1]
                }
                if (tmp==null)
                    return emptyList()
                else return groupAndProducts("modifier")[groups.find { it.id==tmp.modifierId}]!!
            }
        }
    }
}

data class OrgsResponse(
    var organisations: List<OrganisationInfo>
)

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