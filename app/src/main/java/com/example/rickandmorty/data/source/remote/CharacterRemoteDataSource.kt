package com.example.rickandmorty.data.source.remote

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.toCharacter
import com.example.rickandmorty.data.source.apiHandler
import com.example.rickandmorty.data.source.remote.api.RickAndMortyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {

    suspend fun getCharacters(page: Int): List<Character> = withContext(Dispatchers.IO) {
        val response = apiHandler { rickAndMortyApi.getCharacters(page) }

        response
            .results
            .map {
                it.toCharacter()
            }
    }
}
