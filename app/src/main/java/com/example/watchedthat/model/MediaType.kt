package com.example.watchedthat.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MediaType(val value: String, val displayName: String) {
    @SerialName("movie")
    MOVIE("movie", "Movie"),
    @SerialName("tv")
    TV_SHOW("tv_show", "TV show"),
    UNKNOWN("unknown", "")
}
