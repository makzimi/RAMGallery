package com.makzimi.ramgallery.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: Long,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String,

    @ColumnInfo(name = "image")
    @field:SerializedName("image")
    val image: String
)