package com.sample.imageloading.domain.datasource

import com.sample.imageloading.domain.entity.Photo
import com.sample.imageloading.domain.entity.PhotoSearchParams
import com.sample.pagination.PaginatedDataSource

abstract class PhotoDataSource : PaginatedDataSource<PhotoSearchParams, Photo>()