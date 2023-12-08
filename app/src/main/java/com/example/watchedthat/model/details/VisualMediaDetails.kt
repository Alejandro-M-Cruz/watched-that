package com.example.watchedthat.model.details

import com.example.watchedthat.model.visualmedia.VisualMedia
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

    val trailerUrl: String?
        get() = videos.results.filter {
            it.isOfficial && it.site == "YouTube" && (it.type == "Trailer" || it.type == "Teaser")
        }.maxByOrNull { it.releaseDate }?.let {
            "https://www.youtube.com/watch?v=${it.path}"
        }

    val originalLanguage: String
        get() = Locale(originalLanguageCode).getDisplayLanguage(Locale.ENGLISH)
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
