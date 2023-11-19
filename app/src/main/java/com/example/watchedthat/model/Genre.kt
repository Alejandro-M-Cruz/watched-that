package com.example.watchedthat.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey
    val id: Int,
    val name: String
)
