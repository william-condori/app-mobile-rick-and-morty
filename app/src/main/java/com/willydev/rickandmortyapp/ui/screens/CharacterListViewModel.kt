package com.willydev.rickandmortyapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willydev.rickandmortyapp.data.model.Character
import com.willydev.rickandmortyapp.data.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface CharacterListUiState {
    object Loading : CharacterListUiState
    data class Success(
        val characters: List<Character>,
        val isNextPageLoading: Boolean = false,
        val error: String? = null
    ) : CharacterListUiState
    data class Error(val message: String) : CharacterListUiState
}

class CharacterListViewModel(private val repository: RickAndMortyRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterListUiState>(CharacterListUiState.Loading)
    val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var endOfPaginationReached = false
    private var isFetching = false

    init {
        loadCharacters(isNextPage = false)
    }

    fun loadNextPage() {
        loadCharacters(isNextPage = true)
    }

    private fun loadCharacters(isNextPage: Boolean) {
        if (isFetching || (isNextPage && endOfPaginationReached)) return

        viewModelScope.launch {
            isFetching = true

            if (isNextPage) {
                _uiState.update { state ->
                    if (state is CharacterListUiState.Success) {
                        state.copy(isNextPageLoading = true, error = null)
                    } else state
                }
            } else {
                _uiState.value = CharacterListUiState.Loading
                currentPage = 1
                endOfPaginationReached = false
            }

            repository.getCharacters(if (isNextPage) currentPage + 1 else 1)
                .onSuccess { response ->
                    val newCharacters = response.results
                    currentPage = if (isNextPage) currentPage + 1 else 1
                    endOfPaginationReached = response.info.next == null

                    _uiState.update { state ->
                        if (isNextPage && state is CharacterListUiState.Success) {
                            CharacterListUiState.Success(
                                characters = state.characters + newCharacters,
                                isNextPageLoading = false
                            )
                        } else {
                            CharacterListUiState.Success(
                                characters = newCharacters,
                                isNextPageLoading = false
                            )
                        }
                    }
                }
                .onFailure { error ->
                    val errorMessage = error.message ?: "Unknown error occurred"
                    if (isNextPage) {
                        _uiState.update { state ->
                            if (state is CharacterListUiState.Success) {
                                state.copy(isNextPageLoading = false, error = errorMessage)
                            } else state
                        }
                    } else {
                        _uiState.value = CharacterListUiState.Error(errorMessage)
                    }
                }

            isFetching = false
        }
    }

    fun retry() {
        if (_uiState.value is CharacterListUiState.Error) {
            loadCharacters(isNextPage = false)
        } else if (_uiState.value is CharacterListUiState.Success) {
            loadCharacters(isNextPage = true)
        }
    }
}