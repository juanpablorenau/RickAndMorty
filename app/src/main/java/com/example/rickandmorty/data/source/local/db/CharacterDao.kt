package com.example.rickandmorty.data.source.local.db

import androidx.room.* // ktlint-disable no-wildcard-imports
import com.example.rickandmorty.data.model.CharacterDbModel

@Dao
interface CharacterDao {

    @Query("SELECT * FROM Characters")
    suspend fun getCharacters(): List<CharacterDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacters(characters: List<CharacterDbModel>)
}
