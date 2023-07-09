package com.makzimi.ramgallery.gallery.data

import com.google.gson.annotations.SerializedName

data class RAMServiceResponse(

    @SerializedName("info")
    val info: ResponseInfo = ResponseInfo(),

    @SerializedName("results")
    val results: List<CharacterEntity> = listOf(),

    @SerializedName("error")
    val error: String? = null
)
