package com.example.rickandmorty.domain.usecase

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class UpdateCharacter @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(character: Character) {
        characterRepository.updateCharacter(character)
    }
}
