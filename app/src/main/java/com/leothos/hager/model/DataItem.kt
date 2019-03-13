package com.leothos.hager.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataItem(
    @field:SerializedName("CategoryId")
    @Expose
    val categoryId: Int? = null,
    @field:SerializedName("Order")
    @Expose
    val order: Int? = null,
    @field:SerializedName("FlagValidUntil")
    @Expose
    val flagValidUntil: Any? = null,
    @field:SerializedName("FlagValidFrom")
    @Expose
	val flagValidFrom: Any? = null,
    @field:SerializedName("ENumber")
    @Expose
	val eNumber: Any? = null,
    @field:SerializedName("Reference")
    @Expose
	val reference: String? = null,
    @field:SerializedName("FlagType")
    @Expose
	val flagType: Int? = null,
    @field:SerializedName("BaseUnit")
    @Expose
	val baseUnit: String? = null,
    @field:SerializedName("PackQuantity")
    @Expose
	val packQuantity: Int? = null,
    @field:SerializedName("MarketStatus")
    @Expose
	val marketStatus: String? = null,
    @field:SerializedName("Brand")
    @Expose
	val brand: String? = null,
    @field:SerializedName("PriceQuantity")
    @Expose
	val priceQuantity: Int? = null,
    @field:SerializedName("Descriptions")
    @Expose
	val descriptions: List<DescriptionsItem?>? = null,
    @field:SerializedName("ShortDescriptions")
    @Expose
	val shortDescriptions: List<ShortDescriptionsItem?>? = null,
    @field:SerializedName("EAN")
    @Expose
	val eAN: String? = null,
    @field:SerializedName("PriceUnit")
    @Expose
	val priceUnit: String? = null,
    @field:SerializedName("Price")
    @Expose
	val price: Double? = null,
    @field:SerializedName("ZPL")
    @Expose
	val zPL: Boolean? = null,
    @field:SerializedName("PriorityImage")
    @Expose
	val priorityImage: Int? = null,
    @field:SerializedName("Id")
    @Expose
	val id: Int? = null,
    @field:SerializedName("PriceCurrency")
    @Expose
	val priceCurrency: String? = null,
    @field:SerializedName("PriceGroup")
    @Expose
	val priceGroup: String? = null
)
