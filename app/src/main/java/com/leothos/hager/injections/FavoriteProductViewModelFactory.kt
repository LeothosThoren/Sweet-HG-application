package com.leothos.hager.injections

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leothos.hager.repository.FavoriteProductDataRepository
import com.leothos.hager.view_models.FavoriteProductViewModel
import java.util.concurrent.Executor


data class FavoriteProductViewModelFactory(
    private val favoriteProductDataRepository: FavoriteProductDataRepository,
    private val executor: Executor
) : ViewModelProvider.Factory {

    @NonNull
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteProductViewModel::class.java)) {
            return FavoriteProductViewModel(favoriteProductDataRepository, executor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}