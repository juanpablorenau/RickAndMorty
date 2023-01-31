package com.example.rickandmorty.data.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class Location(
    val name: String = "",
    val url: String = ""
)

data class LocationApiModel(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)

data class LocationDbModel(
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "url") val url: String?
)

fun LocationApiModel.toLocation(): Location =
    Location(
        name = name ?: "",
        url = url ?: ""
    )

fun LocationDbModel.toLocation(): Location =
    Location(
        name = name ?: "",
        url = url ?: ""
    )

fun Location.toDbModel(): LocationDbModel =
    LocationDbModel(
        name = name,
        url = url
    )
