package com.example.watchedthat.model.genre

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "genres")
@Serializable
data class Genre(
    @PrimaryKey
    val id: Int,
    val name: String
)
