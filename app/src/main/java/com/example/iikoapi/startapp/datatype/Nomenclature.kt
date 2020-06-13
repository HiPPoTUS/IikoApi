package com.example.iikoapi.startapp.datatype

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName


@JsonIgnoreProperties(ignoreUnknown = true)
data class Group (
    @SerializedName("id")
    var id: String = "root", /*Guid Уникальный идентификатор*/
    @SerializedName("name")
    var name: String = "rootName", /*string Название*/
    @SerializedName("code")
    var code: String?, /*string Артикул*/
    @SerializedName("description")
    val description: String = "noDescription", /*string Описание*/
    @SerializedName("order")
    var order: Int? /*int Порядок отображения*/,
    @SerializedName("parentGroup")
    var parentGroup: String = "root", /*Guid Родительская группа*/
    @SerializedName("images")
    var images: List<Image> = emptyList<Image>(), /*ImageInfo[] URLs картинок*/
//    var imageId: String, /*Guid Идентификатор картинки(устарело)*/
    @SerializedName("isIncludedInMenu")
    var isIncludedInMenu: String?, /*bool Нужно ли группу отображать в дереве номенклатуры*/
    @SerializedName("additionalInfo")
    var additionalInfo: String?, /*string Дополнительная информация*/
    @SerializedName("tags")
    var tags: List<String> = emptyList<String>(), /*String[] Тэги*/
    @SerializedName("seoDescription")
    var seoDescription: String?, /*string SEO-описание для клиента*/
    @SerializedName("seoKeywords")
    var seoKeywords: String?, /*string SEO-ключевые слова*/
    @SerializedName("seoText")
    var seoText: String?, /*string SEO-текст для роботов*/
    @SerializedName("seoTitle")
    var seoTitle: String? /*string SEO-заголовок*/

)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Product (
    @SerializedName("id")
    var id: String /*Guid Уникальный идентификатор*/,
    @SerializedName("name")
    var name: String /*Название*/,
    @SerializedName("code")
    var code: String = "0000" /*Артикул*/,
    @SerializedName("description")
    var description: String = "EMPTY"/*Описание*/,
    @SerializedName("order")
    var order: Int? /*Порядок отображения*/,
    @SerializedName("parentGroup")
    var parentGroup: String?/*Родительская группа*/,
    @SerializedName("images")
    var images: List<Image> = emptyList<Image>() /*Описание картинок*/,
    @SerializedName("groupId")
//    var imageId Guid Идентификатор картинки(устарело)
    var groupId: String? /*Идентификатор группы*/,
    @SerializedName("productCategoryId")
    var productCategoryId: String? /*Идентификатор категории продукта*/,
    @SerializedName("price")
    var price: Int = 0/*Цена*/,
    @SerializedName("carbohydrateAmount")
    var carbohydrateAmount: Double? /*Количество углеводов на 100 г блюда*/,
    @SerializedName("energyAmount")
    var energyAmount: Double? /*Энергетическая ценность на 100 г блюда*/,
    @SerializedName("fatAmount")
    var fatAmount: Double? /* decimal Количество жиров на 100 г блюда*/,
    @SerializedName("fiberAmount")
    var fiberAmount: Double? /* decimal Количество белков на 100 г блюда*/,
    @SerializedName("carbohydrateFullAmount")
    var carbohydrateFullAmount: Double? /* decimal Количество углеводов в блюде*/,
    @SerializedName("energyFullAmount")
    var energyFullAmount: Double? /* decimal Энергетическая ценность в блюде*/,
    @SerializedName("fatFullAmount")
    var fatFullAmount: Double? /* decimal Количество жиров в блюде*/,
    @SerializedName("fiberFullAmount")
    var fiberFullAmount: Double? /* decimal Количество белков в блюде*/,
    @SerializedName("weight")
    var weight: Double = 0.0 /* decimal Вес одной единицы в кг*/,
    @SerializedName("type")
    var type: String = "good" /* string Тип: dish good modifier*/,
    @SerializedName("isIncludedInMenu")
    var isIncludedInMenu: Boolean = true /*Нужно ли продукт отображать в дереве номенклатуры*/,
    @SerializedName("modifiers")
    var modifiers: List<Modifer> = emptyList<Modifer>() /*Одиночные модификаторы*/,
    @SerializedName("groupModifiers")
    var groupModifiers: List<Modifer> = emptyList<Modifer>()/* Групповые модификаторы*/,
    @SerializedName("additionalInfo")
    var additionalInfo: String? /* string Дополнительная информация*/,
    @SerializedName("tags")
    var tags: List<String>? /*Тэги*/,
    @SerializedName("MeasureUnit")
    var MeasureUnit: String? /* string Единица измерения товара ( кг, л, шт, порц.)*/,
    @SerializedName("doNotPrintInCheque")
    var doNotPrintInCheque: Boolean? /*Блюдо не нужно печатать на чеке. Актуально только для модификаторов.*/,
    @SerializedName("seoDescription")
    var seoDescription: String? /* string SEO-описание для клиента*/,
    @SerializedName("seoKeywords")
    var seoKeywords: String? /* string SEO-ключевые слова*/,
    @SerializedName("seoText")
    var seoText: String? /* string SEO-текст для роботов*/,
    @SerializedName("seoTitle")
    var seoTitle: String? /* string SEO-заголовок*/,
    @SerializedName("prohibitedToSaleOn")
    var prohibitedToSaleOn: List<String>? /*Список ID терминалов, на которых продукт запрещен к продаже*/,
    @SerializedName("differentPricesOn")
    var differentPricesOn: List<CustomTerminalPriceInfo>? /*CustomTerminalPriceInfo[] Список терминалов, на которых цена продукта отличается от стандартной и цен на них.*/,
    @SerializedName("useBalanceForSell")
    var useBalanceForSell: Boolean? /*Товар продается на вес*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Image (
    @SerializedName("imageId")
    var imageId: String /*Guid Идентификатор картинки */,
    @SerializedName("imageUrl")
    var imageUrl: String /*URL для загрузки картинки */,
    @SerializedName("uploadDate")
    var uploadDate: String? /*Дата выгрузки картинки в формате "yyyy-MM-dd HH:mm:ss"*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Modifer (
    @SerializedName("modifierId")
    var modifierId: String /* Guid Идентификатор модификатора. Идентификатор продукта для одиночного модификатора и идентификатор группы - для группового.*/,
    @SerializedName("maxAmount")
    var maxAmount: Double? /* int Максимальное количество модификатора*/,
    @SerializedName("minAmount")
    var minAmount: Double? /* int Минимальное количество модификатора*/,
    @SerializedName("defaultAmount")
    var defaultAmount: Double? /* decimal Количество по умолчанию*/,
    @SerializedName("hideIfDefaultAmount")
    var hideIfDefaultAmount: Boolean?/* boolean Признак того, что не нужно отображать список модификаторов, если их количество равно количеству*/,
    @SerializedName("childModifiersHaveMinMaxRestrictions")
    var childModifiersHaveMinMaxRestrictions: Boolean? /* boolean Признак того, что дочерние модификаторы имеют ограничения. Актуально только для групповых модификаторов.*/
)