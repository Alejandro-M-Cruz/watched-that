package com.example.watchedthat.model.details

import com.example.watchedthat.Constants
import com.example.watchedthat.model.genre.Genre
import com.example.watchedthat.model.visual_media.VisualMedia
import com.example.watchedthat.network.VisualMediaDetailsSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable(with= VisualMediaDetailsSerializer::class)
interface VisualMediaDetails : VisualMedia {
    val originalTitle: String
    val overview: String
    val originalLanguageCode: String
    val websiteUrl: String?
    val videos: VideoResults
    val genres: List<Genre>

    override val genreIds: List<Int>
        get() = genres.map { it.id }

    val trailerPath: String?
        get() = videos.results.filter {
            it.isOfficial && it.site == "YouTube" && (it.type == "Trailer" || it.type == "Teaser")
        }.maxByOrNull { it.releaseDate }?.path

    val trailerUrl: String?
        get() = trailerPath?.let { "${Constants.BaseVideoUrl}$it" }

    val originalLanguage: String
        get() = Locale(originalLanguageCode).getDisplayLanguage(Locale.ENGLISH)

    val formattedGenres: String
        get() = genres.joinToString { it.name }
}

@Serializable
data class VideoResults(
    val results: List<Video>
)

@Serializable
data class Video(
    @SerialName("key")
    val path: String,
    val site: String,
    val size: Int,
    val type: String,
    @SerialName("official")
    val isOfficial: Boolean,
    @SerialName("published_at")
    val releaseDate: String
)
