package com.leothos.hager.injections

import android.content.Context
import com.leothos.hager.database.FavoriteProductDatabase
import com.leothos.hager.repository.FavoriteProductDataRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Necessary to provide all necessary class without taking car of the constructor
 * The Factory class will take care of it for us
 * */
object Injection {

    private fun provideProductDataSource(context: Context): FavoriteProductDataRepository {
        val database = FavoriteProductDatabase.getInstance(context)
        return FavoriteProductDataRepository(database!!.favoriteProductDao())
    }

    private fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun viewModelFactory(context: Context): FavoriteProductViewModelFactory {
        val dataSourceFavoriteProduct = provideProductDataSource(context)
        val executor = provideExecutor()
        return FavoriteProductViewModelFactory(dataSourceFavoriteProduct, executor)
    }
}