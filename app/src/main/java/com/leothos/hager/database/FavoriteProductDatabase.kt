package com.leothos.hager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.leothos.hager.model.entities.FavoriteProduct


@Database(entities = [FavoriteProduct::class], version = 1, exportSchema = false)
abstract class FavoriteProductDatabase : RoomDatabase() {

    // --- DAO ---
    abstract fun myProductDao(): FavoriteProductDao

    companion object {

        // --- SINGLETON ---
        @Volatile
        private var INSTANCE: FavoriteProductDatabase? = null

        // --- INSTANCE ---
        fun getInstance(context: Context): FavoriteProductDatabase? {
            if (INSTANCE == null) {
                synchronized(FavoriteProductDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            FavoriteProductDatabase::class.java, "HGDatabase.db"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}