package com.example.watchedthat.network

import com.example.watchedthat.model.Genre
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(val genres: List<Genre>)