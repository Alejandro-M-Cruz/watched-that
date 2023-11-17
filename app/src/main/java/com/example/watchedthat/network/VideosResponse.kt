package com.example.watchedthat.network

import com.example.watchedthat.model.Video
import kotlinx.serialization.Serializable

@Serializable
data class VideosResponse(
    val results: List<Video>
) {
    val trailer: Video?
        get() {
            val officialYoutubeVideos = results.filter {
                it.isOfficial && it.site == "YouTube"
            }
            return officialYoutubeVideos.find { it.type == "Trailer" } ?:
                officialYoutubeVideos.find { it.type == "Teaser" }
        }
}
