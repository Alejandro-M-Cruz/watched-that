package com.example.watchedthat.unused

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val id: String,
    val name: String,
    @SerialName("key")
    val path: String,
    val site: String,
    val type: String,
    @SerialName("official")
    val isOfficial: Boolean,
    @SerialName("published_at")
    val publishedAt: String
)