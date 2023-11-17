package com.example.watchedthat.model

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "genres")
data class Genre(
    val id: Int,
    val name: String
)
