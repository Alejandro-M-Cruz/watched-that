package com.example.watchedthat.unused

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.watchedthat.model.MediaType
import com.example.watchedthat.model.VisualMedia

@Entity(tableName = "visual_media", primaryKeys = ["id", "media_type"])
data class SavedVisualMedia(
    override val id: Int,
    override val title: String,
    @ColumnInfo(name = "genre_ids")
    override val genreIds: List<Int>,
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
    override val mediaType: MediaType
) : VisualMedia {
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
