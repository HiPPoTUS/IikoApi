package com.example.iikoapi.entities.datatype

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties
//
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//data class Group (
//    var id: String = "root", /*Guid Уникальный идентификатор*/
//    var name: String = "rootName", /*string Название*/
//    var code: String?, /*string Артикул*/
//    val description: String = "noDescription", /*string Описание*/
//    var order: Int? /*int Порядок отображения*/,
//    var parentGroup: String = "root", /*Guid Родительская группа*/
//    var images: List<Image> = emptyList<Image>(), /*ImageInfo[] URLs картинок*/
////    var imageId: String, /*Guid Идентификатор картинки(устарело)*/
//    var isIncludedInMenu: Boolean?, /*bool Нужно ли группу отображать в дереве номенклатуры*/
//    var additionalInfo: String?, /*string Дополнительная информация*/
//    var tags: List<String> = emptyList<String>(), /*String[] Тэги*/
//    var seoDescription: String?, /*string SEO-описание для клиента*/
//    var seoKeywords: String?, /*string SEO-ключевые слова*/
//    var seoText: String?, /*string SEO-текст для роботов*/
//    var seoTitle: String? /*string SEO-заголовок*/
//
//)
//@JsonIgnoreProperties(ignoreUnknown = true)
//data class Product (
//    var id: String /*Guid Уникальный идентификатор*/,
//    var name: String /*Название*/,
//    var code: String = "0000" /*Артикул*/,
//    var description: String = "EMPTY"/*Описание*/,
//    var order: Int? /*Порядок отображения*/,
//    var parentGroup: String?/*Родительская группа*/,
//    var images: List<Image> = emptyList<Image>() /*Описание картинок*/,
////    var imageId Guid Идентификатор картинки(устарело)
//    var groupId: String? /*Идентификатор группы*/,
//    var productCategoryId: String? /*Идентификатор категории продукта*/,
//    var price: Int = 0/*Цена*/,
//    var carbohydrateAmount: Double? /*Количество углеводов на 100 г блюда*/,
//    var energyAmount: Double? /*Энергетическая ценность на 100 г блюда*/,
//    var fatAmount: Double? /* decimal Количество жиров на 100 г блюда*/,
//    var fiberAmount: Double? /* decimal Количество белков на 100 г блюда*/,
//    var carbohydrateFullAmount: Double? /* decimal Количество углеводов в блюде*/,
//    var energyFullAmount: Double? /* decimal Энергетическая ценность в блюде*/,
//    var fatFullAmount: Double? /* decimal Количество жиров в блюде*/,
//    var fiberFullAmount: Double? /* decimal Количество белков в блюде*/,
//    var weight: Double = 0.0 /* decimal Вес одной единицы в кг*/,
//    var type: String = "good" /* string Тип: dish good modifier*/,
//    var isIncludedInMenu: Boolean = true /*Нужно ли продукт отображать в дереве номенклатуры*/,
//    var modifiers: List<Modifer> = emptyList<Modifer>() /*Одиночные модификаторы*/,
//    var groupModifiers: List<Modifer> = emptyList<Modifer>()/* Групповые модификаторы*/,
//    var additionalInfo: String? /* string Дополнительная информация*/,
//    var tags: List<String>? /*Тэги*/,
//    var MeasureUnit: String? /* string Единица измерения товара ( кг, л, шт, порц.)*/,
//    var doNotPrintInCheque: Boolean? /*Блюдо не нужно печатать на чеке. Актуально только для модификаторов.*/,
//    var seoDescription: String? /* string SEO-описание для клиента*/,
//    var seoKeywords: String? /* string SEO-ключевые слова*/,
//    var seoText: String? /* string SEO-текст для роботов*/,
//    var seoTitle: String? /* string SEO-заголовок*/,
//    var prohibitedToSaleOn: List<ID> /*Список ID терминалов, на которых продукт запрещен к продаже*/,
//    var differentPricesOn: List<CustomTerminalPriceInfo>? /*CustomTerminalPriceInfo[] Список терминалов, на которых цена продукта отличается от стандартной и цен на них.*/,
//    var useBalanceForSell: Boolean? /*Товар продается на вес*/
//)
//@JsonIgnoreProperties(ignoreUnknown = true)
data class Image(
    var imageId: String? = null /*Guid Идентификатор картинки */,
    var imageUrl: String /*URL для загрузки картинки */,
    var uploadDate: String? = null/*Дата выгрузки картинки в формате "yyyy-MM-dd HH:mm:ss"*/
) : Serializable

//@JsonIgnoreProperties(ignoreUnknown = true)
//data class Modifer (
//    var modifierId: String /* Guid Идентификатор модификатора. Идентификатор продукта для одиночного модификатора и идентификатор группы - для группового.*/,
//    var maxAmount: Double? /* int Максимальное количество модификатора*/,
//    var minAmount: Double? /* int Минимальное количество модификатора*/,
//    var defaultAmount: Double? /* decimal Количество по умолчанию*/,
//    var hideIfDefaultAmount: Boolean?/* boolean Признак того, что не нужно отображать список модификаторов, если их количество равно количеству*/,
//    var childModifiersHaveMinMaxRestrictions: Boolean? /* boolean Признак того, что дочерние модификаторы имеют ограничения. Актуально только для групповых модификаторов.*/
//)
//
//data class ID(
//    var terminalId: String?
//)
data class Group(
    val additionalInfo: Any? = null,
    val code: Any? = null,
    val description: Any? = null,
    val id: String? = null,

    @get:JsonProperty("isDeleted") @field:JsonProperty("isDeleted")
    val isDeleted: Boolean? = null,

    val name: String? = null,
    val seoDescription: Any? = null,
    val seoKeywords: Any? = null,
    val seoText: Any? = null,
    val seoTitle: Any? = null,
    val tags: Any? = null,
    val images: List<Any?>? = null,

    @get:JsonProperty("isIncludedInMenu") @field:JsonProperty("isIncludedInMenu")
    val isIncludedInMenu: Boolean? = null,

    val order: Long? = null,
    val parentGroup: Any? = null
)

data class ProductCategory(
    val id: String? = null,
    val name: String? = null
)

data class Product(
    val additionalInfo: Any? = null,
    val code: String? = null,
    val description: String? = null,
    val id: String? = null,

    @get:JsonProperty("isDeleted") @field:JsonProperty("isDeleted")
    val isDeleted: Boolean? = null,

    val name: String? = null,
    val seoDescription: Any? = null,
    val seoKeywords: Any? = null,
    val seoText: Any? = null,
    val seoTitle: Any? = null,
    val tags: Any? = null,
    val carbohydrateAmount: Double? = null,
    val carbohydrateFullAmount: Double? = null,
    val differentPricesOn: List<Any?>? = null,
    val doNotPrintInCheque: Boolean? = null,
    val energyAmount: Double? = null,
    val energyFullAmount: Double? = null,
    val fatAmount: Double? = null,
    val fatFullAmount: Double? = null,
    val fiberAmount: Double? = null,
    val fiberFullAmount: Double? = null,

    @get:JsonProperty("groupId") @field:JsonProperty("groupId")
    val groupID: String? = null,

    val groupModifiers: List<GroupModifier>? = null,
    val measureUnit: String? = null,
    val modifiers: List<Modifier>? = null,
    val price: Int? = null,

    @get:JsonProperty("productCategoryId") @field:JsonProperty("productCategoryId")
    val productCategoryID: String? = null,

    val prohibitedToSaleOn: List<Any?>? = null,
    val type: String? = null,
    val useBalanceForSell: Boolean? = null,
    val weight: Double? = null,
    val images: List<Image> = emptyList<Image>(),

    @get:JsonProperty("isIncludedInMenu") @field:JsonProperty("isIncludedInMenu")
    val isIncludedInMenu: Boolean? = null,

    val order: Long? = null,
    val parentGroup: String? = null,
    val warningType: Long? = null
)

data class GroupModifier(
    val maxAmount: Long? = null,
    val minAmount: Long? = null,

    @get:JsonProperty("modifierId") @field:JsonProperty("modifierId")
    val modifierID: String? = null,

    val required: Boolean? = null,
    val childModifiers: List<Modifier>? = null,
    val childModifiersHaveMinMaxRestrictions: Boolean? = null
)

data class Modifier(
    val maxAmount: Long? = null,
    val minAmount: Long? = null,

    @get:JsonProperty("modifierId") @field:JsonProperty("modifierId")
    val modifierID: String? = null,

    val required: Boolean? = null,
    val defaultAmount: Long? = null,
    val hideIfDefaultAmount: Boolean? = null
)

