package com.example.rickandmorty.domain.usecase

import com.example.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class DeleteCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(id: Int) {
        characterRepository.deleteCharacter(id)
    }
}
