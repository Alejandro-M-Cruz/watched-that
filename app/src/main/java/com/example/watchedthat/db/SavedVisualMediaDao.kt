package com.example.watchedthat.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.watchedthat.model.visualmedia.SavedVisualMedia
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedVisualMediaDao {
    @Query(
        "SELECT * FROM visual_media " +
        "WHERE title LIKE '%' || :query || '%' AND added_to_wishlist_at IS NOT NULL"
    )
    fun searchInWishlist(query: String): Flow<List<SavedVisualMedia>>

    @Query(
        "SELECT * FROM visual_media " +
        "WHERE title LIKE '%' || :query || '%' AND watched_at IS NOT NULL"
    )
    fun searchInWatchedList(query: String): Flow<List<SavedVisualMedia>>

    @Query(
        "SELECT * FROM visual_media " +
        "WHERE added_to_wishlist_at IS NOT NULL ORDER BY added_to_wishlist_at DESC"
    )
    fun getWishlist(): Flow<List<SavedVisualMedia>>

    @Query(
        "SELECT * FROM visual_media " +
        "WHERE watched_at IS NOT NULL ORDER BY watched_at DESC"
    )
    fun getWatched(): Flow<List<SavedVisualMedia>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(savedVisualMedia: SavedVisualMedia)

    @Delete
    suspend fun delete(savedVisualMedia: SavedVisualMedia)

    @Transaction
    suspend fun addToWishList(savedVisualMedia: SavedVisualMedia) {
        savedVisualMedia.addToWishlist()
        insertOrReplace(savedVisualMedia)
    }

    @Transaction
    suspend fun markAsWatched(savedVisualMedia: SavedVisualMedia) {
        savedVisualMedia.markAsWatched()
        insertOrReplace(savedVisualMedia)
    }

    @Transaction
    suspend fun removeFromWishlist(savedVisualMedia: SavedVisualMedia) {
        savedVisualMedia.removeFromWishlist()
        delete(savedVisualMedia)
    }

    @Transaction
    suspend fun unmarkAsWatched(savedVisualMedia: SavedVisualMedia) {
        savedVisualMedia.unmarkAsWatched()
        delete(savedVisualMedia)
    }
}