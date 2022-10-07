package com.example.movies.models

import kotlinx.serialization.Serializable

@Serializable
class Image(
    val id: Int,
    val name: String,
    val image: String
)