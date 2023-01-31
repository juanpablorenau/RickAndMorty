package com.example.rickandmorty.data.source.local

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.toCharacter
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
}
