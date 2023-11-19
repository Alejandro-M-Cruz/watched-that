package com.example.watchedthat.unused

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "genre_relations", primaryKeys = ["visualMediaId", "genreId"])
data class GenreRelation(
    @ColumnInfo(name = "visual_media_id")
    val visualMediaId: Int,
    @ColumnInfo(name = "genre_id")
    val genreId: Int
)
