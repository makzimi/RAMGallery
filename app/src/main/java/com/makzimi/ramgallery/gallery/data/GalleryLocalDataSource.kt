package com.makzimi.ramgallery.gallery.data

import androidx.paging.DataSource
import com.makzimi.ramgallery.model.CharacterEntity
import java.util.concurrent.Executor

class GalleryLocalDataSource(
    private val dao: GalleryDao,
    private val executor: Executor
) {
    fun getAllCharacters(): DataSource.Factory<Int, CharacterEntity> {
        return dao.queryAllCharacters()
    }

    fun insert(characters: List<CharacterEntity>, onFinished: () -> Unit) {
        executor.execute {
            dao.insert(characters)
            onFinished()
        }
    }

    fun insertAndDeleteTheRest(characters: List<CharacterEntity>, onFinished: () -> Unit) {
        executor.execute {
            dao.deleteAllAndInsert(characters)
            onFinished()
        }
    }
}