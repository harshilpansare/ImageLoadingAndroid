package com.sample.imageloading.data.entity

import com.google.gson.annotations.SerializedName

class PhotosSearchResponse(@SerializedName("photos") val photosResponse: PhotosResponse)

class PhotosResponse(@SerializedName("photo") val photos: ArrayList<PhotoEntity>,
                     @SerializedName("page") val page: Int,
                     @SerializedName("total") val total: String)

class PhotoEntity(@SerializedName("id") val id: String,
                  @SerializedName("title") val title: String,
                  @SerializedName("farm") val farm: Int,
                  @SerializedName("server") val server: String,
                  @SerializedName("secret") val secret: String)
