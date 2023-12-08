package com.example.watchedthat.data

import com.example.watchedthat.db.SavedVisualMediaDao
import com.example.watchedthat.model.MediaType
import com.example.watchedthat.model.visualmedia.SavedVisualMedia
import kotlinx.coroutines.flow.Flow

interface SavedVisualMediaRepository {
    fun getByIdAndMediaType(id: Int, mediaType: MediaType): Flow<SavedVisualMedia?>
    fun getWishlist(): Flow<List<SavedVisualMedia>>
    fun getWatchedList(): Flow<List<SavedVisualMedia>>
    fun searchInWishlist(query: String): Flow<List<SavedVisualMedia>>
    fun searchInWatchedList(query: String): Flow<List<SavedVisualMedia>>
    suspend fun addToWishlist(savedVisualMedia: SavedVisualMedia)
    suspend fun addToWatchedList(savedVisualMedia: SavedVisualMedia)
    suspend fun removeFromWishlist(savedVisualMedia: SavedVisualMedia)
    suspend fun removeFromWatchedList(savedVisualMedia: SavedVisualMedia)
}

class OfflineSavedVisualMediaRepository(
    private val savedVisualMediaDao: SavedVisualMediaDao
) : SavedVisualMediaRepository {
    override fun getByIdAndMediaType(id: Int, mediaType: MediaType): Flow<SavedVisualMedia?> {
        return savedVisualMediaDao.getByIdAndMediaType(id, mediaType)
    }

    override fun getWishlist(): Flow<List<SavedVisualMedia>> {
        return savedVisualMediaDao.getWishlist()
    }

    override fun getWatchedList(): Flow<List<SavedVisualMedia>> {
        return savedVisualMediaDao.getWatched()
    }

    override fun searchInWishlist(query: String): Flow<List<SavedVisualMedia>> {
        return savedVisualMediaDao.searchInWishlist(query)
    }

    override fun searchInWatchedList(query: String): Flow<List<SavedVisualMedia>> {
        return savedVisualMediaDao.searchInWatchedList(query)
    }

    override suspend fun addToWishlist(savedVisualMedia: SavedVisualMedia) {
        savedVisualMediaDao.addToWishList(savedVisualMedia)
    }

    override suspend fun addToWatchedList(savedVisualMedia: SavedVisualMedia) {
        savedVisualMediaDao.markAsWatched(savedVisualMedia)
    }

    override suspend fun removeFromWishlist(savedVisualMedia: SavedVisualMedia) {
        savedVisualMediaDao.removeFromWishlist(savedVisualMedia)
    }

    override suspend fun removeFromWatchedList(savedVisualMedia: SavedVisualMedia) {
        savedVisualMediaDao.unmarkAsWatched(savedVisualMedia)
    }
}


