package com.leothos.hager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.leothos.hager.model.entities.FavoriteProduct


@Dao
interface FavoriteProductDao {

    @Query("SELECT * FROM FavoriteProduct ORDER BY referenceId DESC")
    fun getAllFavoritesProduct(): LiveData<List<FavoriteProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteProduct(favoriteProduct: FavoriteProduct): Long

    @Update
    fun updateFavoriteProduct(favoriteProduct: FavoriteProduct): Int

    @Query("DELETE FROM FavoriteProduct WHERE referenceId= :referenceId")
    fun deleteFavoriteProduct(referenceId: String)
}