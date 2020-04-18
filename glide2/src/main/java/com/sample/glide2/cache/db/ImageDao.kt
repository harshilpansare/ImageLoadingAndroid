package com.sample.glide2.cache.db

import androidx.room.Dao
import androidx.room.Query

@Dao
abstract class ImageDao : BaseDao<ImageEntity>() {
    @Query("select * from images where url = :url limit 1")
    abstract fun getImage(url: String): ImageEntity

    @Query("select * from images order by created_at asc limit 1")
    abstract fun getOldestImage(): ImageEntity

    @Query("select count(*) from images")
    abstract fun getImagesCount() : Int

    @Query("DELETE FROM images")
    abstract fun deleteAll()
}