package com.sample.imageloading.domain.entity

import com.sample.pagination.entity.PaginationEntity

data class Photo(val id: String,
                 val title: String,
                 val imageUrl: String): PaginationEntity(id, null)

data class PhotoSearchParams(var searchTerm: String)