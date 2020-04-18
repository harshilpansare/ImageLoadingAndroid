package com.sample.glide2.cache

import androidx.annotation.CallSuper

abstract class Cache<K, V>(private val maxItems: Int) {

    abstract fun contains(key: K): Boolean

    abstract fun get(key: K): V?

    @CallSuper
    open fun insert(key: K, value: V) {
        if (currentSize() > maxItems)
            removeFirstItem()
    }

    abstract fun clear()

    abstract fun removeFirstItem(): K

    abstract fun currentSize(): Int
}
