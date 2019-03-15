package com.leothos.hager

import java.text.SimpleDateFormat
import java.util.*


/**
 * dateFormatterEn display the pattern yyyy-MM-dd'T'HH:mm:ss and return a string value
 * @param cal: Calendar
 * */
fun dateFormatterEn(cal: Calendar): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return sdf.format(cal.time)
}

/**
 * dateFormatterEn display the pattern dd/MM/yyyy HH:mm:ss and return a string value
 * @param str: String
 * */
fun dateFormatterFR(str: String): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    val toFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return sdf.format(toFormat.parse(str))
}

