package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.source.local.CharacterLocalDataSource
import com.example.rickandmorty.data.source.remote.CharacterRemoteDataSource
import com.example.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource
) : CharacterRepository {

    // Api
    override suspend fun getCharacters(): List<Character> {
        val roomCharacters = characterLocalDataSource.getCharacters()
        return roomCharacters.ifEmpty {
            var page = 1
            val allCharacters = mutableListOf<Character>()

            do {
                val list =
                    characterRemoteDataSource.getCharacters(page++).also { saveCharacters(it) }
                allCharacters.addAll(list)
            } while (list.isNotEmpty())

            allCharacters
        }
    }

    // Room
    override suspend fun saveCharacters(characters: List<Character>) {
        characterLocalDataSource.saveCharacters(characters)
    }

    override suspend fun getCharacterById(id: Int): Character {
        return characterLocalDataSource.getCharacterById(id)
    }

    override suspend fun updateCharacter(character: Character) {
        characterLocalDataSource.updateCharacter(character)
    }
}
