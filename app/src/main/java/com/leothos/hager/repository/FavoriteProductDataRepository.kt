package com.leothos.hager.repository

import androidx.lifecycle.LiveData
import com.leothos.hager.database.FavoriteProductDao
import com.leothos.hager.model.entities.FavoriteProduct


data class FavoriteProductDataRepository(private val myProductDao: FavoriteProductDao) {

    // --- GET ---

    fun getAllFavoriteProducts(): LiveData<List<FavoriteProduct>> = this.myProductDao.getAllFavoritesProduct()


    // --- CREATE ---

    fun insertFavoriteProduct(myProduct: FavoriteProduct) {
        myProductDao.insertFavoriteProduct(myProduct)
    }

    // --- UPDATE ---

    fun updateFavoriteProduct(myProduct: FavoriteProduct) {
        myProductDao.updateFavoriteProduct(myProduct)
    }

    // --- DELETE ---

    fun deleteFavoriteProduct(referenceId: String) {
        myProductDao.deleteFavoriteProduct(referenceId)
    }

}