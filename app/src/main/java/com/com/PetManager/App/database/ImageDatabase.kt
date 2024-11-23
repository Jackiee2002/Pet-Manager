package com.kroger.classapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kroger.classapp.dao.ImageDao
import com.kroger.classapp.viewmodel.ImageModel


@Database(
    entities = [ImageModel::class],
    version = 1,
    exportSchema = false
)

abstract class ImageDatabase: RoomDatabase() {
    abstract val imageDao: ImageDao
}