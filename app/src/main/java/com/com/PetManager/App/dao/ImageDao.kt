package com.kroger.classapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kroger.classapp.viewmodel.ImageModel


@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageModel: ImageModel)

    @Query("SELECT * FROM images")
    fun getAllImages(): LiveData<List<ImageModel>>
}