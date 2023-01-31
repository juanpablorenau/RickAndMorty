package com.example.rickandmorty.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.App
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.Empty
import com.example.rickandmorty.data.model.NoInternetError
import com.example.rickandmorty.data.model.ResultError
import com.example.rickandmorty.domain.usecase.GetCharactersUseCase
import com.example.rickandmorty.helpers.extensions.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CharactersUiState(
    val items: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: ResultError = Empty
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    val uiState by lazy {
        MutableStateFlow(CharactersUiState(isLoading = true))
    }

    init {
        getCharacters()
    }

    private fun getCharacters() = viewModelScope.launch {
        setLoadingState(true)

        if (App.instance.isNetworkAvailable) {
            setItemsState(getCharactersUseCase(1))
            setLoadingState(false)
        } else {
            setErrorState(NoInternetError)
        }
    }

    private fun setItemsState(list: List<Character>) {
        uiState.update {
            it.copy(
                items = list,
                isLoading = false,
                error = Empty
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

    private fun setErrorState(error: ResultError) {
        uiState.update {
            it.copy(
                error = error,
                isLoading = false
            )
        }
    }

    fun notifyLastVisible() = viewModelScope.launch {
        val page = (uiState.value.items.size / 20) + 1
        if (App.instance.isNetworkAvailable) {
            setItemsState(uiState.value.items + getCharactersUseCase(page))
        } else {
            setErrorState(NoInternetError)
        }
    }
}
