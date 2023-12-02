package com.example.watchedthat.model.details

import com.example.watchedthat.model.MediaType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    override val id: Int,
    override val title: String,
    @SerialName("release_date")
    override val releaseDate: String,
    @SerialName("vote_average")
    override val rating: Float,
    @SerialName("vote_count")
    override val ratingCount: Int,
    override val popularity: Float,
    @SerialName("poster_path")
    override val posterPath: String? = null,
    @SerialName("backdrop_path")
    override val backdropPath: String? = null,
    @SerialName("original_title")
    override val originalTitle: String,
    val budget: Long,
    val revenue: Long,
    override val overview: String,
    @SerialName("original_language")
    override val originalLanguage: String,
    @SerialName("homepage")
    override val website: String?,
    override val videos: VideoResults,
    @SerialName("runtime")
    val runtimeInMinutes: Int,
) : VisualMediaDetails {
    override val mediaType = MediaType.MOVIE
}
