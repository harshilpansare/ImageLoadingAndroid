package com.sample.glide2.cache.db

import android.content.Context
import android.graphics.Bitmap
import com.sample.glide2.cache.Cache

class DbCache(context: Context, maxItems: Int): Cache<String, Bitmap>(maxItems) {

    private var imageDatabase: ImageDatabase = ImageDatabase.getInstance(context)!!

    override fun contains(key: String): Boolean {
        return get(key) != null
    }

    override fun get(key: String): Bitmap? {
        return null
    }

    override fun insert(key: String, value: Bitmap) {
        val filePath = ""
        val imageEntity = ImageEntity(key, filePath)
        imageDatabase.imageDao().upsertItem(imageEntity)
        super.insert(key, value)
    }

    override fun clear() {
        imageDatabase.imageDao().deleteAll()
    }

    override fun removeFirstItem(): String {
        val image = imageDatabase.imageDao().getOldestImage()
        imageDatabase.imageDao().deleteItem(image)
        return image.url
    }

    override fun currentSize(): Int {
        return imageDatabase.imageDao().getImagesCount()
    }
}
