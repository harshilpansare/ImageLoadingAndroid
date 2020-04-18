package com.sample.imageloading.di

import com.sample.imageloading.data.network.ApiFactory
import com.sample.imageloading.data.network.FLICKR_API
import com.sample.imageloading.data.network.FlickrApi
import com.sample.imageloading.data.repository.PhotoRemoteRepository
import com.sample.imageloading.domain.datasource.PhotoDataSource

object PhotoDataSourceProvider {

    private fun getFlickrApi(): FlickrApi {
        return ApiFactory.createRetrofitService(FlickrApi::class.java, FLICKR_API)
    }

    fun getFlickrPhotoDataSource(): PhotoDataSource {
        return PhotoRemoteRepository(getFlickrApi())
    }
}