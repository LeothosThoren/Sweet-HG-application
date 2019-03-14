package com.leothos.hager.model.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteProduct(

    @PrimaryKey
    @NonNull
    val referenceId: String,
    var brand: String?,
    val description: String?,
    val price: Double?,
    val currency: String?,
    val eAN: String?
)