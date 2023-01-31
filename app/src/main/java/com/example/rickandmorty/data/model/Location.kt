package com.example.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class Location(
    val name: String = "",
    val url: String = ""
)

data class LocationApiModel(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)

fun LocationApiModel.toLocation(): Location =
    Location(
        name = name ?: "",
        url = url ?: ""
    )
