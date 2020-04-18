package com.sample.glide2.cache

import android.content.Context
import android.graphics.Bitmap
import com.sample.glide2.cache.db.DbCache
import com.sample.glide2.cache.memory.LruCache

const val CACHE_SIZE = 20

class Glide2Cache(context: Context, maxItems: Int): Cache<String, Bitmap>(maxItems) {

    private val bitmapCache = LruCache<String, Bitmap>(CACHE_SIZE)
    private val dbCache = DbCache(context, CACHE_SIZE)

    override fun contains(key: String): Boolean {
        return bitmapCache.contains(key)
    }

    override fun get(key: String): Bitmap? {
        return bitmapCache.get(key)
    }

    override fun insert(key: String, value: Bitmap) {
        bitmapCache.insert(key, value)
        super.insert(key, value)
    }

    override fun clear() {
        bitmapCache.clear()
    }

    override fun removeFirstItem(): String {
        val firstKey = bitmapCache.removeFirstItem()
        return firstKey
    }

    override fun currentSize(): Int {
        return bitmapCache.currentSize()
    }

}