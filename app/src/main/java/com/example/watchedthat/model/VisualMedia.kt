package com.example.watchedthat.model

import com.example.watchedthat.Constants
import com.example.watchedthat.network.VisualMediaSerializer
import kotlinx.serialization.Serializable

@Serializable(with = VisualMediaSerializer::class)
interface VisualMedia {
    val id: Int
    val title: String
    val releaseDate: String
    val genreIds: List<Int>
    val rating: Float
    val ratingCount: Int
    val popularity: Float
    val posterPath: String?
    val backdropPath: String?
    val mediaType: MediaType

    val posterUrl: String?
        get() = posterUrl?.let { "${Constants.BaseImageUrl}/$it" }

    val backdropUrl: String?
        get() = backdropUrl?.let { "${Constants.BaseImageUrl}/$it" }

    fun toSavedVisualMedia() = SavedVisualMedia(
        id = id,
        title = title,
        genreIds = genreIds,
        rating = rating,
        ratingCount = ratingCount,
        releaseDate = releaseDate,
        popularity = popularity,
        posterPath = posterPath,
        backdropPath = backdropPath,
        mediaType = mediaType
    )
}
