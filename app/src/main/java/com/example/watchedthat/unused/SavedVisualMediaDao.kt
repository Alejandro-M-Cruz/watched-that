package com.example.watchedthat.unused

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.watchedthat.unused.SavedVisualMedia
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedVisualMediaDao {
    @Query("SELECT * FROM visual_media WHERE id = :id")
    fun get(id: Int): Flow<SavedVisualMedia>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(savedVisualMedia: SavedVisualMedia)

    @Delete
    suspend fun delete(savedVisualMedia: SavedVisualMedia)

    suspend fun addToWishList(savedVisualMedia: SavedVisualMedia) {
        savedVisualMedia.addToWishlist()
        insertOrReplace(savedVisualMedia)
    }

    suspend fun markAsWatched(savedVisualMedia: SavedVisualMedia) {
        savedVisualMedia.markAsWatched()
        insertOrReplace(savedVisualMedia)
    }

    suspend fun removeFromWishlist(savedVisualMedia: SavedVisualMedia) {
        savedVisualMedia.removeFromWishlist()
        delete(savedVisualMedia)
    }

    suspend fun unmarkAsWatched(savedVisualMedia: SavedVisualMedia) {
        savedVisualMedia.unmarkAsWatched()
        delete(savedVisualMedia)
    }
}