package com.leothos.hager

import java.text.SimpleDateFormat
import java.util.*

/**
 * dateFormatter display the pattern yyyy-MM-dd'T'HH:mm:ss and return a string value
 * @param cal: Calendar
 * */
fun dateFormatter(cal: Calendar): String  {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return sdf.format(cal.time)
}

/**
 * dateFormatter display the pattern dd/MM/yyyy HH:mm:ss and return a string value
 * @param str: String
 * */
fun dateFormatter2(cal: Calendar): String {
     val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(cal.time)
}