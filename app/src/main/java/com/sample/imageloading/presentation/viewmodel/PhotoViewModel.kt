package com.sample.imageloading.presentation.viewmodel

import com.sample.imageloading.domain.datasource.PhotoDataSource
import com.sample.imageloading.domain.entity.Photo
import com.sample.imageloading.domain.entity.PhotoSearchParams
import com.sample.pagination.PaginatedViewModel

class PhotoViewModel(dataSource: PhotoDataSource) : PaginatedViewModel<PhotoSearchParams, Photo>(dataSource) {

    fun init(searchTerm: String) {
        init(PhotoSearchParams(searchTerm))
    }

    companion object {
        const val DEFAULT_SEARCH_TERM = "Corona"
    }
}