package com.leothos.hager.web_services

import com.leothos.hager.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    /**
     * Classic Retrofit configurations
     * */
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: Api = retrofit.create(
        Api::class.java)
}