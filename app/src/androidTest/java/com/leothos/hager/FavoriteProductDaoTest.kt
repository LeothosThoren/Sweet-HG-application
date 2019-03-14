package com.leothos.hager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.leothos.hager.database.FavoriteProductDatabase
import com.leothos.hager.model.entities.FavoriteProduct
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class FavoriteProductDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    //Data
    private var database: FavoriteProductDatabase? = null

    @Before
    @Throws(Exception::class)
    fun initDb() {
        this.database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            FavoriteProductDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        database!!.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun getMyProductWhenNoItemInserted() {
        // TEST
        val myProducts = LiveDataTestUtil.getValue(this.database!!.favoriteProductDao().getAllFavoriteProducts())
        assertTrue(myProducts.isEmpty())
    }


    @Test
    @Throws(InterruptedException::class)
    fun insertAndGetMyProducts() {
        // BEFORE : Adding

        this.database!!.favoriteProductDao().insertFavoriteProduct(NEW_PRODUCT0)
        this.database!!.favoriteProductDao().insertFavoriteProduct(NEW_PRODUCT1)
        this.database!!.favoriteProductDao().insertFavoriteProduct(NEW_PRODUCT2)

        // TEST
        val myProducts = LiveDataTestUtil.getValue(this.database!!.favoriteProductDao().getAllFavoriteProducts())
        assertTrue(myProducts.size == 3)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndUpdateMyProduct() {
        // BEFORE : Adding. Next, update item added & re-save it
        this.database!!.favoriteProductDao().insertFavoriteProduct(NEW_PRODUCT0)
        val favoriteProduct =
            LiveDataTestUtil.getValue(this.database!!.favoriteProductDao().getAllFavoriteProducts()).get(0)
        favoriteProduct.brand = "HAGER"
        this.database!!.favoriteProductDao().updateFavoriteProduct(favoriteProduct)

        //TEST
        val myProducts = LiveDataTestUtil.getValue(this.database!!.favoriteProductDao().getAllFavoriteProducts())
        assertEquals("HAGER", myProducts.get(0).brand)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndDeleteRealEstate() {
        // BEFORE : added & delete it.
        this.database!!.favoriteProductDao().insertFavoriteProduct(NEW_PRODUCT0)
        val myProduct = LiveDataTestUtil.getValue(this.database!!.favoriteProductDao().getAllFavoriteProducts()).get(0)
        myProduct.brand = "HAGER"
        this.database!!.favoriteProductDao().deleteFavoriteProduct(myProduct.referenceId)

        //TEST
        val myProducts = LiveDataTestUtil.getValue(this.database!!.favoriteProductDao().getAllFavoriteProducts())
        assertTrue(myProducts.isEmpty())
    }

    companion object {

        // DATA SET FOR TEST
        private var NEW_PRODUCT0 = FavoriteProduct(
            "AB1234", "HAGER", "lamp", 53.23,
            "EUR", "1234567891234"
        )
        private val NEW_PRODUCT1 = FavoriteProduct(
            "CD4567", "Deloitte", "smartphone", 93.93,
            "EUR", "1234567891234"
        )

        private val NEW_PRODUCT2 = FavoriteProduct(
            "EF8910", "Legrand", "prise électrique", 59.99,
            "EUR", "1234567891234"

        )
    }
}