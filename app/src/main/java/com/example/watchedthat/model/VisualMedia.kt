package com.example.watchedthat.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "visual_media", primaryKeys = ["id", "mediaType"])
class VisualMedia(
    val id: Int,
    val title: String,
    val genres: List<Genre>,
    val rating: Float,
    @ColumnInfo(name = "rating_count")
    val ratingCount: Int,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    val popularity: Float,
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = null,
    @ColumnInfo(name = "media_type")
    val mediaType: MediaType
)
