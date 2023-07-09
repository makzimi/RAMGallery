package com.makzimi.ramgallery.gallery.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class CharactersRepository(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val localDataSource: CharactersLocalDataSource
) {
    companion object {
        const val DATABASE_PAGE = 30
    }

    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var isInProgress = false
    private var nextPage = 1

    fun getAllCharacters(): LiveData<PagedList<CharacterEntity>> {

        val dataSourceFactory = localDataSource.getAllCharacters()

        val boundaryCallback = CharactersBoundaryCallback(::fetchNextPage)

        val config = PagedList.Config.Builder()
            .setPageSize(DATABASE_PAGE)
            .setEnablePlaceholders(false)
            .build()

        val data = LivePagedListBuilder(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return data
    }

    private fun fetchNextPage() {
        if (isInProgress) return
        changeIsProgress(true)
        remoteDataSource.getRemoteCharacters(nextPage,
            {
                localDataSource.insert(it) {
                    nextPage += 1
                    changeIsProgress(false)
                }
            },
            {
                _errors.postValue(it)
                changeIsProgress(false)
            }
        )
    }

    fun refreshWithFirstPage() {
        if (isInProgress) return
        nextPage = 1
        changeIsProgress(true)
        remoteDataSource.getRemoteCharacters(nextPage,
            {
                localDataSource.insertAndDeleteTheRest(it) {
                    nextPage += 1
                    changeIsProgress(false)
                }
            },
            {
                _errors.postValue(it)
                changeIsProgress(false)
            }
        )
    }

    private fun changeIsProgress(newState: Boolean) {
        synchronized(isInProgress) {
            isInProgress = newState
            _isLoading.postValue(newState)
        }
    }
}