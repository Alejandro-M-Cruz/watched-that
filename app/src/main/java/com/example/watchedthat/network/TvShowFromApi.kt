package com.example.watchedthat.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TvShowFromApi(
    val id: Int,
    val name: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("vote_average")
    val rating: Float,
    @SerialName("vote_count")
    val ratingCount: Int,
    @SerialName("first_air_date")
    val firstAirDate: String,
    val popularity: Float,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
)

