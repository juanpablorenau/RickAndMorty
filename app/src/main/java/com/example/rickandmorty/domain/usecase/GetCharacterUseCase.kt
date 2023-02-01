package com.example.rickandmorty.domain.usecase

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.resultOf
import com.example.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(id: Int): Result<Character> {
        return resultOf {
            characterRepository.getCharacterById(id)
        }
    }
}
