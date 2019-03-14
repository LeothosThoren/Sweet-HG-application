package com.leothos.hager.injections

import android.content.Context
import com.leothos.hager.database.FavoriteProductDatabase
import com.leothos.hager.repository.FavoriteProductDataRepository
import com.leothos.hager.view_models.FavoriteProductViewModel
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object Injection {

    fun provideProductDataSource(context: Context): FavoriteProductDataRepository {
        val database = FavoriteProductDatabase.getInstance(context)
        return FavoriteProductDataRepository(database!!.myProductDao())
    }

    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun provideFavoriteProductViewModelFactory(context: Context): FavoriteProductViewModel {
        val dataSourceFavoriteProduct = provideProductDataSource(context)
        val executor = provideExecutor()
        return FavoriteProductViewModel(dataSourceFavoriteProduct, executor)
    }
}