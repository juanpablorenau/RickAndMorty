package com.example.rickandmorty.domain.repository

import com.example.rickandmorty.data.model.Character

interface CharacterRepository {

    suspend fun getCharacters(page: Int): List<Character>

    suspend fun getCharacter(id: Int): Character

    suspend fun saveCharacters()
}
