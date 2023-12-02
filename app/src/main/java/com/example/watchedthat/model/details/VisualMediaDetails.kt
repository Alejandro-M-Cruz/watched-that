package com.example.watchedthat.model.details

import com.example.watchedthat.model.visualmedia.VisualMedia
import com.example.watchedthat.network.VisualMediaDetailsSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with= VisualMediaDetailsSerializer::class)
interface VisualMediaDetails : VisualMedia {
    val originalTitle: String
    val overview: String
    val originalLanguage: String
    val website: String?
    val videos: VideoResults

    val trailerUrl: String?
        get() = videos.results.filter {
            it.isOfficial && it.site == "YouTube" && (it.type == "Trailer" || it.type == "Teaser")
        }.maxByOrNull { it.releaseDate }?.let {
            "https://www.youtube.com/watch?v=${it.path}"
        }
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
