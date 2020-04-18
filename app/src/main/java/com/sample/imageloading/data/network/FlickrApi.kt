package com.sample.imageloading.data.network

import com.sample.imageloading.data.entity.PhotosSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val FLICKR_API = "https://api.flickr.com/"

interface FlickrApi {

    @GET("services/rest?method=flickr.photos.search&api_key=062a6c0c49e4de1d78497d13a7dbb360&text=cat&format=json&nojsoncallback=1")
    fun fetchPhotos(@Query("text") searchTerm: String,
                    @Query("page") page: Int,
                    @Query("limit") limit: Int): Single<PhotosSearchResponse>
}