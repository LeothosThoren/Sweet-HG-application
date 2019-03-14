package com.leothos.hager.repository

import androidx.lifecycle.LiveData
import com.leothos.hager.database.FavoriteProductDao
import com.leothos.hager.model.entities.FavoriteProduct


data class FavoriteProductDataRepository(
    private val favoriteProductDao: FavoriteProductDao
) {

    // --- GET ---

    fun getAllFavoriteProducts(): LiveData<List<FavoriteProduct>> {
        return favoriteProductDao.getAllFavoriteProducts()
    }

    // --- CREATE ---

    fun insertFavoriteProduct(favoriteProduct: FavoriteProduct) {
        favoriteProductDao.insertFavoriteProduct(favoriteProduct)
    }

    // --- UPDATE ---

    fun updateFavoriteProduct(favoriteProduct: FavoriteProduct) {
        favoriteProductDao.updateFavoriteProduct(favoriteProduct)
    }

    // --- DELETE ---

    fun deleteFavoriteProduct(referenceId: String) {
        favoriteProductDao.deleteFavoriteProduct(referenceId)
    }

}