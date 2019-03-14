package com.leothos.hager.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiProductsResponse(
    @field:SerializedName("Data")
    @Expose
    val data: List<ApiProductItem?>? = null,
    @field:SerializedName("LastSync")
    @Expose
    val lastSync: String? = null,
    @field:SerializedName("ErrorMessage")
    @Expose
    val errorMessage: Any? = null,
    @field:SerializedName("HasError")
    @Expose
    val hasError: Boolean? = null
)
