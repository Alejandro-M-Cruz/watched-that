package com.example.watchedthat.data

import com.example.watchedthat.db.SavedVisualMediaDao
import com.example.watchedthat.model.SavedVisualMedia
import kotlinx.coroutines.flow.Flow

interface SavedVisualMediaRepository {
    fun getWishlist(): Flow<List<SavedVisualMedia>>
    fun getWatchedList(): Flow<List<SavedVisualMedia>>
    suspend fun addToWishList(savedVisualMedia: SavedVisualMedia)
    suspend fun addToWatchedList(savedVisualMedia: SavedVisualMedia)
    suspend fun removeFromWishlist(savedVisualMedia: SavedVisualMedia)
    suspend fun removeFromWatchedList(savedVisualMedia: SavedVisualMedia)
}

class OfflineSavedVisualMediaRepository(
    private val savedVisualMediaDao: SavedVisualMediaDao
) : SavedVisualMediaRepository {
    override fun getWishlist(): Flow<List<SavedVisualMedia>> {
        return savedVisualMediaDao.getWishlist()
    }

    override fun getWatchedList(): Flow<List<SavedVisualMedia>> {
        return savedVisualMediaDao.getWatched()
    }

    private suspend fun insertOrReplace(savedVisualMedia: SavedVisualMedia) {
        savedVisualMediaDao.insertOrReplace(savedVisualMedia)
    }

    override suspend fun addToWishList(savedVisualMedia: SavedVisualMedia) {
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


