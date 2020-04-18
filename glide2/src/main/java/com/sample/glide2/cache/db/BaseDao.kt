package com.sample.glide2.cache.db

import androidx.room.*

abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertItem(item: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertItems(items: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract fun updateItem(item: T)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract fun updateItems(item: List<T>)

    @Delete
    abstract fun deleteItem(item: T)

    @Transaction
    open fun upsertItem(item: T) {
        val id = insertItem(item)
        if (id.toInt() == -1) {
            updateItem(item)
        }
    }

    @Transaction
    open fun upsertItems(list: List<T>) {
        val ids = insertItems(list)
        ids.forEachIndexed { index, element ->
            if (element.toInt() == -1) {
                updateItem(list[index])
            }
        }
    }
}