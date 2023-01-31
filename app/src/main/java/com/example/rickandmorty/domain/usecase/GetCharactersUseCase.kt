package com.example.rickandmorty.domain.usecase

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase@Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(): List<Character> {
        return characterRepository.getCharacters()
    }
}
