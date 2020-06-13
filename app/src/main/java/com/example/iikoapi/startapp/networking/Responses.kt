package com.example.iikoapi.startapp.networking

import android.util.Log
import com.example.iikoapi.startapp.datatype.Group
import com.example.iikoapi.startapp.datatype.Modifer
import com.example.iikoapi.startapp.datatype.OrganisationInfo
import com.example.iikoapi.startapp.datatype.Product
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
