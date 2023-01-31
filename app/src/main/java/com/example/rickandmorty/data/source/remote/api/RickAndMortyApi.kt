package com.example.rickandmorty.data.source.remote.api

import com.example.rickandmorty.data.model.ResponseCharacters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("api/character")
    suspend fun getCharacters(@Query("page") page: Int): Response<ResponseCharacters>
}
