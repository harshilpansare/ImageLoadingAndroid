package com.sample.imageloading.data.mapper

import com.sample.imageloading.data.entity.PhotoEntity
import com.sample.imageloading.data.entity.PhotosResponse
import com.sample.imageloading.data.entity.PhotosSearchResponse
import com.sample.imageloading.domain.entity.Photo
import com.sample.pagination.entity.PageData
import com.sample.pagination.entity.PageMetaData

object PhotoMapper {
    fun transform(response: PhotosSearchResponse): PageData<Photo> {
        val photos = ArrayList<Photo>(response.photosResponse.photos.size)
        for (photo in response.photosResponse.photos)
            photos.add(transformPhoto(photo))
        return PageData(photos, PageMetaData(response.photosResponse.page, response.photosResponse.total.toInt()))
    }

    private fun transformPhoto(photoEntity: PhotoEntity): Photo {
        return Photo(photoEntity.id,
            photoEntity.title,
            String.format("https://farm%s.staticflickr.com/%s/%s_%s_m.jpg",
                photoEntity.farm, photoEntity.server, photoEntity.id,  photoEntity.secret))
    }
}