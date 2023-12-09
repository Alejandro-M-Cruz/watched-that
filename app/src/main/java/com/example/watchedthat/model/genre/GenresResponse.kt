package com.example.watchedthat.model.genre

import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(val genres: List<Genre>)
