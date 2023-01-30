package com.example.rickandmorty.data.model

data class Character(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val location: Location = Location(),
    val image: String = "",
    val episodes: List<String> = listOf()
)
