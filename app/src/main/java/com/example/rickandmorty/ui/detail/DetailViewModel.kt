package com.example.rickandmorty.ui.detail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.Empty
import com.example.rickandmorty.data.model.ResultError
import com.example.rickandmorty.domain.usecase.DeleteCharacterUseCase
import com.example.rickandmorty.domain.usecase.GetCharacterUseCase
import com.example.rickandmorty.domain.usecase.UpdateCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.rickandmorty.data.model.Character as Character

data class DetailUiState(
    val character: Character = Character(),
    val isLoading: Boolean = false,
    val error: ResultError = Empty
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val updateCharacterUseCase: UpdateCharacterUseCase,
    private val deleteCharacterUseCase: DeleteCharacterUseCase
) : ViewModel() {

    val uiState by lazy {
        MutableStateFlow(DetailUiState(isLoading = true))
    }

    fun initData(data: Bundle) {
        val id = data.getInt(DetailActivity.INTENT_ID, 0)
        getCharacterById(id)
    }

    fun saveCharacter(character: Character) {
        viewModelScope.launch {
            updateCharacterState(character)
            updateCharacterUseCase(uiState.value.character)
        }
    }

    fun deleteCharacter() {
        viewModelScope.launch {
            deleteCharacterUseCase(uiState.value.character.id)
        }
    }

    private fun getCharacterById(id: Int) {
        viewModelScope.launch {
            setLoadingState(true)
            getCharacterUseCase(id)
                .onSuccess { character ->
                    setSuccessState(character)
                }
                .onFailure { error ->
                    setErrorState(error as ResultError)
                }
            setLoadingState(false)
        }
    }

    private fun updateCharacterState(character: Character) {
        uiState.update {
            it.copy(
                character = character
            )
        }
    }

    private fun setSuccessState(character: Character) {
        uiState.update {
            it.copy(
                character = character,
                isLoading = false,
                error = Empty
            )
        }
    }

    private fun setErrorState(error: ResultError) {
        uiState.update {
            it.copy(
                error = error,
                isLoading = false
            )
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }
}
