package com.leothos.hager.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DescriptionsItem(
	@field:SerializedName("Language")
	@Expose
	val language: Int? = null,
	@field:SerializedName("Value")
	@Expose
	val value: String? = null,
	@field:SerializedName("ProductId")
	@Expose
	val productId: Int? = null,
	@field:SerializedName("Id")
	@Expose
	val id: Int? = null
)
