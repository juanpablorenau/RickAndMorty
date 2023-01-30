package com.example.rickandmorty.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.model.Character

data class CharactersUiState(
    val items: List<Character> = emptyList(),
    val isLoading: Boolean = false
)

class MainViewModel : ViewModel() {

    val characterList = MutableLiveData<MutableList<Character>>(mutableListOf())

    init {
        characterList.value?.addAll(
            listOf<Character>(
                Character(name = "Rick", status = "Alive", species = "Human"),
                Character(name = "Morty", status = "Alive", species = "Human"),
                Character(name = "Rick", status = "Alive", species = "Human"),
                Character(name = "Morty", status = "Alive", species = "Human"),
                Character(name = "Rick", status = "Alive", species = "Human"),
                Character(name = "Morty", status = "Alive", species = "Human"),
                Character(name = "Rick", status = "Alive", species = "Human"),
                Character(name = "Morty", status = "Alive", species = "Human")
            )
        )
    }
}
