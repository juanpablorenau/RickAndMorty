package com.example.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

class Response

data class ResponseCharacters(
    @SerializedName("info") val info: InfoResponse? = InfoResponse(),
    @SerializedName("results") val results: List<CharacterApiModel> = listOf()
)

data class InfoResponse(
    @SerializedName("count") var count: Int? = 0,
    @SerializedName("pages") var pages: Int? = 0,
    @SerializedName("next") var next: String? = "null",
    @SerializedName("prev") var prev: String? = ""
)

inline fun <R> resultOf(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (error: ResultError) {
        Result.failure(error)
    }
}
