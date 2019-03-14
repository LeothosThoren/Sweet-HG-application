package com.leothos.hager.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.leothos.hager.model.entities.FavoriteProduct
import com.leothos.hager.repository.FavoriteProductDataRepository
import java.util.concurrent.Executor


class FavoriteProductViewModel(
    private val productDataRepository: FavoriteProductDataRepository,
    private val executor: Executor
) : ViewModel() {

    // --- GET ---

    fun getAllFavoriteProducts(): LiveData<List<FavoriteProduct>> {
        return productDataRepository.getAllFavoriteProducts()
    }


    // --- CREATE ---

    fun insertFavoriteProduct(favoriteProduct: FavoriteProduct) {
        executor.execute { productDataRepository.insertFavoriteProduct(favoriteProduct) }
    }

    // --- UPDATE ---

    fun updateFavoriteProduct(favoriteProduct: FavoriteProduct) {
        executor.execute { productDataRepository.updateFavoriteProduct(favoriteProduct) }
    }

    // --- DELETE ---

    fun deleteFavoriteProduct(referenceId: String) {
        executor.execute { productDataRepository.deleteFavoriteProduct(referenceId) }
    }

}