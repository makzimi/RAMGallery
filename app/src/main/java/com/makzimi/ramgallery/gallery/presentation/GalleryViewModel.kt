package com.makzimi.ramgallery.gallery.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.makzimi.ramgallery.gallery.data.GalleryRepository
import com.makzimi.ramgallery.gallery.data.CharacterEntity

class GalleryViewModel(
    private val repository: GalleryRepository
) : ViewModel() {

    val characters: LiveData<PagedList<CharacterEntity>> = repository.getAllGalleryCharacters()
    val errors = repository.errors
    val isLoadingState = repository.isLoading

    fun refresh() {
        repository.refreshWithFirstPage()
    }

}