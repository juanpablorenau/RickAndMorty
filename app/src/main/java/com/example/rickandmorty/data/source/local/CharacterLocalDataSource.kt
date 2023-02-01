package com.example.rickandmorty.data.source.local

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.toCharacter
import com.example.rickandmorty.data.model.toDbModel
import com.example.rickandmorty.data.source.local.db.CharacterDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterLocalDataSource @Inject constructor(
    private val dao: CharacterDao
) {
    suspend fun getCharacters(): List<Character> = withContext(Dispatchers.IO) {
        dao.getCharacters()
            .map { it.toCharacter() }
    }

    suspend fun saveCharacters(character: List<Character>) = withContext(Dispatchers.IO) {
        dao.saveCharacters(
            character.map {
                it.toDbModel()
            }
        )
    }

    suspend fun getCharacterById(id: Int): Character = withContext(Dispatchers.IO) {
        dao
            .getCharacterById(id)
            .toCharacter()
    }

    suspend fun updateCharacter(character: Character) = withContext(Dispatchers.IO) {
        dao.updateCharacter(character.toDbModel())
    }

    suspend fun deleteCharacter(id: Int) = withContext(Dispatchers.IO) {
        dao.deleteCharacter(id)
    }
}
