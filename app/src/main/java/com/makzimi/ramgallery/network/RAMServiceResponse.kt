package com.makzimi.ramgallery.network

import com.google.gson.annotations.SerializedName
import com.makzimi.ramgallery.model.CharacterEntity

data class RAMServiceResponse (

    @SerializedName("info")
    val info: ResponseInfo = ResponseInfo(),

    @SerializedName("results")
    val results: List<CharacterEntity> = listOf(),

    @SerializedName("error")
    val error: String? = null
)
