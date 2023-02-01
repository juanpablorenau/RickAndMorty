package com.example.rickandmorty.domain.repository

import com.example.rickandmorty.data.model.Character

interface CharacterRepository {

    // Api
    suspend fun getCharacters(): List<Character>

    // Room
    suspend fun saveCharacters(characters: List<Character>)

    suspend fun getCharacterById(id: Int): Character
}
