package com.makzimi.ramgallery.gallery.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersRemoteDataSource(
    private val api: RAMService
) {

    fun getRemoteCharacters(
        page: Int,
        onSuccess: (data: List<CharacterEntity>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        api.getCharacters(page)
            .enqueue(object : Callback<RAMServiceResponse> {
                override fun onResponse(
                    call: Call<RAMServiceResponse>,
                    response: Response<RAMServiceResponse>
                ) {
                    if (response.isSuccessful) {
                        onSuccess(response.body()?.results ?: emptyList())
                    } else {
                        onError(response.errorBody()?.string() ?: "Unknown Error")
                    }

                }

                override fun onFailure(call: Call<RAMServiceResponse>, t: Throwable) {
                    onError(t.message ?: "Unknown Error")
                }
            })
    }
}