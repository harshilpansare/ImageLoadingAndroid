package com.sample.glide2.cache.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "images")
data class ImageEntity(@PrimaryKey val url: String,
                       @ColumnInfo(name = "file_path") val filePath: String,
                       @ColumnInfo(name = "created_at") val createdAt: Long? = DateConverter.toTimestamp(Date(System.currentTimeMillis())))