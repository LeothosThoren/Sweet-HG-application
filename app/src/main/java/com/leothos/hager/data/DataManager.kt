package com.leothos.hager.data

import com.leothos.hager.model.api.ApiProductItem

object DataManager {
    var dataItems = ArrayList<ApiProductItem>()
    val countryMap = HashMap<String, String>()

    init {
        countryMap.apply {
            put("France", "0")
            put("Germany", "1")
            put("England", "2")
            put("Luxembourg", "3")
            put("Portugal", "4")
            put("Spain", "5")
            put("Czech Republic", "6")
            put("Italy", "7")
        }
    }
}