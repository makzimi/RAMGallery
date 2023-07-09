package com.makzimi.ramgallery.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makzimi.ramgallery.gallery.data.GalleryRepository
import com.makzimi.ramgallery.gallery.ui.GalleryViewModel

class CustomViewModelFactory(private val repository: GalleryRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GalleryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}