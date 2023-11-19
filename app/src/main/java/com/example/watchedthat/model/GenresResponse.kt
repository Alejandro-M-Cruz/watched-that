package com.example.watchedthat.model

import com.example.watchedthat.model.Genre
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(val genres: List<Genre>)