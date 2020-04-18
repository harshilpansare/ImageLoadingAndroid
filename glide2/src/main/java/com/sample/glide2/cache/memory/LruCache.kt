package com.sample.glide2.cache.memory

import com.sample.glide2.cache.Cache

class LruCache<K, V>(maxItems: Int): Cache<K, V>(maxItems) {

    private val cache = LinkedHashMap<K, V>()

    override fun contains(key: K) = cache.containsKey(key)

    override fun get(key: K) = cache[key]

    override fun insert(key: K, value: V) {
        cache[key] = value
        super.insert(key, value)
    }

    override fun currentSize(): Int = cache.size

    override fun clear() {
        cache.clear()
    }

    override fun removeFirstItem(): K {
        val firstKey = cache.keys.iterator().next()
        cache.remove(firstKey)
        return firstKey
    }

}
