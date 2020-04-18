package com.sample.glide2.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImageEntity::class], version = 5)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    companion object {
        private var INSTANCE: ImageDatabase? = null
        fun getInstance(context: Context): ImageDatabase? {
            if (INSTANCE == null) {
                synchronized(ImageDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ImageDatabase::class.java, "images.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
