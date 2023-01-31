package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.source.remote.CharacterRemoteDataSource
import com.example.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): List<Character> {
        return characterRemoteDataSource.getCharacters(page)
    }

    override suspend fun getCharacter(id: Int): Character {
        return Character()
    }

    override suspend fun saveCharacters() {
    }
}
