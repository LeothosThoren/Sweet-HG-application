package com.leothos.hager.web_services

import com.leothos.hager.model.Response
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
    ): Call<Response>
}