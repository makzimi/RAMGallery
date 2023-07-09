package com.makzimi.ramgallery.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makzimi.ramgallery.gallery.data.CharactersRepository
import com.makzimi.ramgallery.gallery.presentation.CharactersViewModel

class CustomViewModelFactory(private val repository: CharactersRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharactersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}