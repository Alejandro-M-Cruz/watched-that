package com.example.watchedthat.network

import com.example.watchedthat.model.VisualMedia
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VisualMediaPagedResponse(
    val page: Int,
    val results: List<VisualMedia>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)
