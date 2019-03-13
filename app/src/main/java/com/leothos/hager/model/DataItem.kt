package com.leothos.hager.model

data class DataItem(
	val categoryId: Int? = null,
	val order: Int? = null,
	val flagValidUntil: Any? = null,
	val flagValidFrom: Any? = null,
	val eNumber: Any? = null,
	val reference: String? = null,
	val flagType: Int? = null,
	val baseUnit: String? = null,
	val packQuantity: Int? = null,
	val marketStatus: String? = null,
	val brand: String? = null,
	val priceQuantity: Int? = null,
	val descriptions: List<DescriptionsItem?>? = null,
	val shortDescriptions: List<ShortDescriptionsItem?>? = null,
	val eAN: String? = null,
	val priceUnit: String? = null,
	val price: Double? = null,
	val zPL: Boolean? = null,
	val priorityImage: Int? = null,
	val id: Int? = null,
	val priceCurrency: String? = null,
	val priceGroup: String? = null
)
