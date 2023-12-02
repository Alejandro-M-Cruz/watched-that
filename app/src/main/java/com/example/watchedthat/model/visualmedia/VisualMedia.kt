package com.example.watchedthat.model.visualmedia

import com.example.watchedthat.Constants
import com.example.watchedthat.model.MediaType
import com.example.watchedthat.network.VisualMediaSerializer
import kotlinx.serialization.Serializable

@Serializable(with = VisualMediaSerializer::class)
interface VisualMedia {
    val id: Int
    val title: String
    val releaseDate: String
    val rating: Float
    val ratingCount: Int
    val popularity: Float
    val mediaType: MediaType
    val posterPath: String?
    val backdropPath: String?

    val posterUrl: String?
        get() = posterPath?.let { "${Constants.BaseImageUrl}$it" }

    val backdropUrl: String?
        get() = backdropPath?.let { "${Constants.BaseImageUrl}$it" }

    fun toSavedVisualMedia() = SavedVisualMedia(
        id = id,
        title = title,
        rating = rating,
        ratingCount = ratingCount,
        releaseDate = releaseDate,
        popularity = popularity,
        posterPath = posterPath,
        backdropPath = backdropPath,
        mediaType = mediaType
    )
}
