package com.leothos.hager.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.leothos.hager.model.entities.FavoriteProduct
import com.leothos.hager.repository.FavoriteProductDataRepository
import java.util.concurrent.Executor


class FavoriteProductViewModel(// REPOSITORY
    private val productDataRepository: FavoriteProductDataRepository, private val mExecutor: Executor
) : ViewModel() {

    // --- GET ---

    fun getAllFavoriteProducts(): LiveData<List<FavoriteProduct>> = productDataRepository.getAllFavoriteProducts()


    // --- CREATE ---

    fun insertFavoriteProduct(myProduct: FavoriteProduct) {
        mExecutor.execute { productDataRepository.insertFavoriteProduct(myProduct) }
    }

    // --- UPDATE ---

    fun updateFavoriteProduct(myProduct: FavoriteProduct) {
        mExecutor.execute { productDataRepository.updateFavoriteProduct(myProduct) }
    }

    // --- DELETE ---

    fun deleteFavoriteProduct(referenceId: String) {
        mExecutor.execute { productDataRepository.deleteFavoriteProduct(referenceId) }
    }

}