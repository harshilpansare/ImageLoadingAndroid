package com.sample.pagination.entity

data class PageData<T>(val list: ArrayList<T>,
                       val metaData: PageMetaData
)