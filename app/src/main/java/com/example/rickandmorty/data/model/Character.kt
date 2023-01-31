package com.example.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class Character(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val location: Location = Location(),
    val image: String = "",
    val episodes: List<String> = listOf()
)

data class CharacterApiModel(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("species") val species: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("origin") val origin: LocationApiModel?,
    @SerializedName("location") val location: LocationApiModel?,
    @SerializedName("image") val image: String?,
    @SerializedName("episode") val episodes: List<String>?,
    @SerializedName("url") val url: String?,
    @SerializedName("created") val created: String?
)

fun CharacterApiModel.toCharacter(): Character =
    Character(
        id = id ?: 0,
        name = name ?: "",
        status = status ?: "",
        species = species ?: "",
        location = location?.toLocation() ?: Location(),
        image = image ?: "",
        episodes = episodes ?: listOf()
    )
