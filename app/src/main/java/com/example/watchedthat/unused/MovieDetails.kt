package com.example.watchedthat.unused

import com.example.watchedthat.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieDetails(
    val id: Int,
    val title: String,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("original_language")
    val originalLanguage: String,
    val genres: List<Genre>,
    @SerialName("vote_average")
    val rating: Float,
    @SerialName("vote_count")
    val ratingCount: Int,
    val popularity: Float,
    val overview: String,
    @SerialName("release_date")
    val releaseDate: String,
    val budget: Int,
    val revenue: Int,
    val runtime: Int,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("homepage")
    val website: String? = null,
    val videos: VideosResponse = VideosResponse(emptyList())
)