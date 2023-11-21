package com.example.watchedthat.network

import com.example.watchedthat.model.MediaType
import com.example.watchedthat.model.VisualMedia
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class VisualMediaPagedResponse(
    val page: Int,
    @SerialName("results")
    val rawResults: List<VisualMedia>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
) {
    @Transient
    val results = rawResults.filter { it.mediaType != MediaType.UNKNOWN }
}
