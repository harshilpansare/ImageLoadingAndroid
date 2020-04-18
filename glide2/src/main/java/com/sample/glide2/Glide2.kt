package com.sample.glide2

import android.content.Context
import android.widget.ImageView
import com.sample.glide2.cache.CACHE_SIZE
import com.sample.glide2.cache.Glide2Cache

class Glide2(context: Context) {

    private val DEFAULT_PLACEHOLDER = R.drawable.ic_placeholder

    private val glide2Cache = Glide2Cache(context, CACHE_SIZE)

    fun loadImage(imageView: ImageView, imageUrl: String, onComplete: () -> Unit) {
        loadImage(imageView, imageUrl, DEFAULT_PLACEHOLDER, onComplete)
    }

    fun loadImage(imageView: ImageView, imageUrl: String, placeholder: Int, onComplete: () -> Unit) {
        if (glide2Cache.contains(imageUrl))
            imageView.setImageBitmap(glide2Cache.get(imageUrl))
        else {
            imageView.setImageResource(placeholder)
            RetrofitImage.getBitmapFromUrl(imageUrl) { bitmap ->
                if (bitmap == null) return@getBitmapFromUrl
                glide2Cache.insert(imageUrl, bitmap)
                onComplete()
            }
        }
    }

    fun clearCache() {
        glide2Cache.clear()
    }
}
