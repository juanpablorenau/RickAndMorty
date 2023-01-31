package com.example.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class ResponseCharacters(
    @SerializedName("info") val info: InfoResponse? = InfoResponse(),
    @SerializedName("results") val results: List<CharacterApiModel> = listOf()
)
