package com.sample.imageloading.data.repository

import com.sample.imageloading.data.mapper.PhotoMapper.transform
import com.sample.imageloading.data.network.FlickrApi
import com.sample.imageloading.domain.datasource.PhotoDataSource
import com.sample.imageloading.domain.entity.Photo
import com.sample.imageloading.domain.entity.PhotoSearchParams
import com.sample.pagination.entity.PaginationProperties.DEFAULT_LIMIT

class PhotoRemoteRepository(private val flickrApi: FlickrApi) : PhotoDataSource() {
    override fun loadPage(
        pageRequest: PhotoSearchParams,
        page: Int,
        callback: LoadCallback<Int, Photo>) {
        executeObservable(flickrApi.fetchPhotos(pageRequest.searchTerm, page, DEFAULT_LIMIT)
            .toObservable()
            .map { transform(it) }, callback)
    }
}
