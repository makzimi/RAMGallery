package com.makzimi.ramgallery.gallery.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RAMService {

    companion object {
        const val ENDPOINT = "https://rickandmortyapi.com/api/"
    }

    @GET("character/")
    fun getCharacters(@Query("page") page: Int): Call<RAMServiceResponse>

}