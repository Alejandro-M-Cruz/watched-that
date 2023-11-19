package com.example.watchedthat.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MediaType(val value: String) {
    @SerialName("movie")
    MOVIE("movie"),
    @SerialName("tv")
    TV_SHOW("tv_show")
}
