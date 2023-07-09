package com.makzimi.ramgallery.gallery.data

import androidx.paging.DataSource
import androidx.room.*
import com.makzimi.ramgallery.model.CharacterEntity

@Dao
interface GalleryDao {

    @Query("SELECT * FROM characters ORDER BY id")
    fun queryAllCharacters(): DataSource.Factory<Int, CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: List<CharacterEntity>): List<Long>

    @Query("DELETE FROM characters")
    fun deleteAll()

    @Transaction
    fun deleteAllAndInsert(characters: List<CharacterEntity>): List<Long> {
        deleteAll()
        return insert(characters)
    }

}