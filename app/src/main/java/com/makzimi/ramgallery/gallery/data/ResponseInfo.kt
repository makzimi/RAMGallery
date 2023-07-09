package com.makzimi.ramgallery.gallery.data

import com.google.gson.annotations.SerializedName

data class ResponseInfo(

    @SerializedName("count")
    val count: Int = 0,

    @SerializedName("pages")
    val pages: Int = 0,

    @SerializedName("next")
    val next: String = "",

    @SerializedName("prev")
    val prev: String = ""
)