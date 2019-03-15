package com.leothos.hager

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {
    @Test
    @Throws(Exception::class)
    fun dateConverter() {
        assertEquals("12/05/2019 12:00:00", dateFormatterFR("2019-05-12T12:00:00"))
    }

}
