package com.example.watchedthat.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index

@Entity(tableName = "visual_media", primaryKeys = ["id", "media_type"], indices = [
    Index(value = ["title"]),
    Index(value = ["added_to_wishlist_at"]),
    Index(value = ["watched_at"])
])
data class SavedVisualMedia(
    override val id: Int,
    override val title: String,
    override val rating: Float,
    @ColumnInfo(name = "rating_count")
    override val ratingCount: Int,
    @ColumnInfo(name = "release_date")
    override val releaseDate: String,
    override val popularity: Float,
    @ColumnInfo(name = "poster_path")
    override val posterPath: String? = null,
    @ColumnInfo(name = "backdrop_path")
    override val backdropPath: String? = null,
    @ColumnInfo(name = "watched_at")
    var watchedAt: String? = null,
    @ColumnInfo(name = "added_to_wishlist_at")
    var addedToWishlistAt: String? = null,
    @ColumnInfo(name = "media_type")
    override val mediaType: MediaType,
) : VisualMedia {
    @Ignore
    override var genreIds: List<Int> = emptyList()

    val watched: Boolean
        get() = watchedAt != null

    val inWishlist: Boolean
        get() = addedToWishlistAt != null

    fun addToWishlist() {
        addedToWishlistAt = System.currentTimeMillis().toString()
        watchedAt = null
    }

    fun removeFromWishlist() {
        addedToWishlistAt = null
    }

    fun markAsWatched() {
        watchedAt = System.currentTimeMillis().toString()
        addedToWishlistAt = null
    }

    fun unmarkAsWatched() {
        watchedAt = null
    }

}
