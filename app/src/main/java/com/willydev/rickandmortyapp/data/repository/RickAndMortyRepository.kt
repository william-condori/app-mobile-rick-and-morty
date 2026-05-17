package com.willydev.rickandmortyapp.data.repository

import com.willydev.rickandmortyapp.data.api.RickAndMortyApiService
import com.willydev.rickandmortyapp.data.model.CharacterResponse

class RickAndMortyRepository(private val apiService: RickAndMortyApiService) {
    suspend fun getCharacters(page: Int? = null): Result<CharacterResponse> {
        return try {
            val response = apiService.getCharacters(page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
