package com.willydev.rickandmortyapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
)
