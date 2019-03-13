package com.leothos.hager.model

data class Response(
	val newProductPrices: NewProductPrices? = null,
	val deletedIds: List<Int?>? = null,
	val getDeletedInSeconds: Double? = null,
	val data: List<DataItem?>? = null,
	val getDataInSeconds: Double? = null,
	val lastSync: String? = null,
	val errorMessage: Any? = null,
	val hasError: Boolean? = null
)
