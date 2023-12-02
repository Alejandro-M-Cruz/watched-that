package com.example.watchedthat.model.visualmedia

import com.example.watchedthat.model.MediaType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShow(
    override val id: Int,
    @SerialName("name")
    override val title: String,
    @SerialName("first_air_date")
    override val releaseDate: String,
    @SerialName("vote_average")
    override val rating: Float,
    @SerialName("vote_count")
    override val ratingCount: Int,
    override val popularity: Float,
    @SerialName("poster_path")
    override val posterPath: String? = null,
    @SerialName("backdrop_path")
    override val backdropPath: String? = null
) : VisualMedia {
    override val mediaType = MediaType.TV_SHOW
}

