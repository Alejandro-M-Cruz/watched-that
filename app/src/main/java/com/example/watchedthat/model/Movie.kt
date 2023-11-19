package com.example.watchedthat.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    override val id: Int,
    override val title: String,
    @SerialName("release_date")
    override val releaseDate: String,
    @SerialName("genre_ids")
    override val genreIds: List<Int>,
    @SerialName("vote_average")
    override val rating: Float,
    @SerialName("vote_count")
    override val ratingCount: Int,
    override val popularity: Float,
    @SerialName("poster_path")
    override val posterPath: String? = null,
    @SerialName("backdrop_path")
    override val backdropPath: String? = null,
) : VisualMedia {
    override val mediaType = MediaType.MOVIE
}
