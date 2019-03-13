package com.leothos.hager.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leothos.hager.web_services.Api
import com.leothos.hager.BASE_URL
import com.leothos.hager.model.DataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsViewModel : ViewModel() {

    val TAG = this::class.java.simpleName

    //Two parameters necessary to call the api
    private lateinit var country: String
    private lateinit var lastSync: String
    //This is the data that will fetch asynchronously
    private var productList = MutableLiveData<com.leothos.hager.model.Response>()

    /**
     * This is the method which will be called to fetch the data
     */
    fun getProducts(country: String, lastSync: String): LiveData<com.leothos.hager.model.Response> {
        if (productList == null) {
            productList = MutableLiveData()

            loadProducts()
        }

        return productList
    }

    /**
     * This method is using Retrofit to get the Json data from URL
     * */
    private fun loadProducts() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(Api::class.java)
        val call = api.getProducts(country, lastSync)

        call.enqueue(object : Callback<com.leothos.hager.model.Response> {
            override fun onFailure(call: Call<com.leothos.hager.model.Response>, t: Throwable) {
                Log.e(TAG, t.toString())
            }

            override fun onResponse(call: Call<com.leothos.hager.model.Response>, response: Response<com.leothos.hager.model.Response>) {
                productList.value = response.body()
                Log.i(TAG, productList.value.toString())
            }
        })

    }

}