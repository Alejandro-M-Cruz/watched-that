package com.example.watchedthat.unused

import com.example.watchedthat.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TvShowDetails(
    val id: Int,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("original_language")
    val originalLanguage: String,
    val genres: List<Genre>,
    @SerialName("vote_average")
    val rating: Float,
    @SerialName("vote_count")
    val ratingCount: Int,
    val popularity: Float,
    val overview: String,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("last_air_date")
    val lastAirDate: String,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerialName("in_production")
    val inProduction: Boolean,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("homepage")
    val website: String? = null,
)