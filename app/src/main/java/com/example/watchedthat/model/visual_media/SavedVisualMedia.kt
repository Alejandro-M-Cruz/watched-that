package com.example.watchedthat.model.visual_media

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.example.watchedthat.model.MediaType
import java.util.Date
import java.util.Locale

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
    @ColumnInfo(name = "genre_ids")
    val genreIdsString: String,
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
    override val genreIds: List<Int>
        get() = genreIdsString.split(",").map { it.toInt() }

    val addedToWishlistMonth: String?
        get() = addedToWishlistAt?.let { isoDateStringToMonth(it) }

    val watchedMonth: String?
        get() = watchedAt?.let { isoDateStringToMonth(it) }

    private fun isoDateStringToMonth(isoDateString: String): String {
        Log.d("isoDateString", isoDateString)
        return SimpleDateFormat("MMMM yyyy", Locale.ENGLISH).format(
            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(isoDateString)
        )
    }

    val watched: Boolean
        get() = watchedAt != null

    val inWishlist: Boolean
        get() = addedToWishlistAt != null

    fun addToWishlist() {
        addedToWishlistAt = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
        watchedAt = null
    }

    fun removeFromWishlist() {
        addedToWishlistAt = null
    }

    fun markAsWatched() {
        watchedAt = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
        addedToWishlistAt = null
    }

    fun unmarkAsWatched() {
        watchedAt = null
    }

}
