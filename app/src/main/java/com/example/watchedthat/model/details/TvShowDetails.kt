package com.example.watchedthat.model.details

import com.example.watchedthat.model.MediaType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowDetails(
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
    override val backdropPath: String? = null,
    @SerialName("original_name")
    override val originalTitle: String,
    override val overview: String,
    @SerialName("original_language")
    override val originalLanguageCode: String,
    override val genres: List<Genre>,
    @SerialName("homepage")
    override val websiteUrl: String?,
    override val videos: VideoResults,
    @SerialName("episode_run_time")
    val episodeRuntimesInMinutes: List<Int>,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerialName("last_air_date")
    val lastAirDate: String?,
    @SerialName("in_production")
    val inProduction: Boolean
) : VisualMediaDetails {
    override val mediaType = MediaType.TV_SHOW

    val formattedEpisodeRuntimes: String
        get() = if (episodeRuntimesInMinutes.isEmpty()) {
            "Unknown"
        } else {
            episodeRuntimesInMinutes
                .joinToString(", ").apply {
                    val lastCommaIndex = lastIndexOf(",")
                    if (lastCommaIndex != -1) {
                        return replaceRange(lastCommaIndex, lastCommaIndex + 1, " or")
                    }
                } + " minutes"
        }

    val isInProduction: String
        get() = if (inProduction) {
            "Yes"
        } else {
            "No"
        }
}
