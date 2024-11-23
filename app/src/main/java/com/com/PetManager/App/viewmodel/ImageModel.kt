package com.kroger.classapp.viewmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageModel(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val imageData: ByteArray,
)