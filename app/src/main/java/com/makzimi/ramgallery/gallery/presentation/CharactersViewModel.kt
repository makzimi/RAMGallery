package com.makzimi.ramgallery.gallery.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.makzimi.ramgallery.gallery.data.CharactersRepository
import com.makzimi.ramgallery.gallery.data.CharacterEntity

class CharactersViewModel(
    private val repository: CharactersRepository
) : ViewModel() {

    val characters: LiveData<PagedList<CharacterEntity>> = repository.getAllCharacters()
    val errors = repository.errors
    val isLoadingState = repository.isLoading

    fun refresh() {
        repository.refreshWithFirstPage()
    }

}