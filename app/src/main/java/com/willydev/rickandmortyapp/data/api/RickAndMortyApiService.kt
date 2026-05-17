package com.willydev.rickandmortyapp.data.api

import com.willydev.rickandmortyapp.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = null
    ): CharacterResponse
}
