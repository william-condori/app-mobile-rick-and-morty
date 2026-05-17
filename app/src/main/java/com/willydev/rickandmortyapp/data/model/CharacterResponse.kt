package com.willydev.rickandmortyapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    val info: Info,
    val results: List<Character>
)

@JsonClass(generateAdapter = true)
data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
