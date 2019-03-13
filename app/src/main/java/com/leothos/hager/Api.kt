package com.leothos.hager

import com.leothos.hager.model.DataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    /**
     * This interface allow to fetch data from URL
     * This url need two parameters as Queries
     * */
    @GET("getproducts")
    fun getProducts(
        @Query("country") country: String,
        @Query("lastsync") lastsync: String
    ): Call<DataItem>
}