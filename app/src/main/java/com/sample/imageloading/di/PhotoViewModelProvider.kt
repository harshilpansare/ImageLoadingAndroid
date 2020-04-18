package com.sample.imageloading.di

import com.sample.imageloading.presentation.viewmodel.PhotoViewModel

object PhotoViewModelProvider {

    fun getPhotoViewModel(): PhotoViewModel {
        return PhotoViewModel(PhotoDataSourceProvider.getFlickrPhotoDataSource())
    }
}