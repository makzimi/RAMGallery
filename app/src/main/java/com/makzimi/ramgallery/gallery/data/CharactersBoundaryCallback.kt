package com.makzimi.ramgallery.gallery.data

import androidx.paging.PagedList

class CharactersBoundaryCallback(
    private val fetchCall: () -> Unit
) : PagedList.BoundaryCallback<CharacterEntity>() {

    override fun onZeroItemsLoaded() {
        fetchCall()
    }

    override fun onItemAtFrontLoaded(itemAtFront: CharacterEntity) {
    }

    override fun onItemAtEndLoaded(itemAtEnd: CharacterEntity) {
        fetchCall()
    }
}