package com.example.watchedthat.data

import com.example.watchedthat.db.SavedVisualMediaDao
import com.example.watchedthat.model.SavedVisualMedia
import kotlinx.coroutines.flow.Flow

interface SavedVisualMediaRepository {
    fun getWishlist(): Flow<List<SavedVisualMedia>>
    fun getWatched(): Flow<List<SavedVisualMedia>>
    suspend fun insertOrReplace(savedVisualMedia: SavedVisualMedia)
    suspend fun addToWishList(savedVisualMedia: SavedVisualMedia)
    suspend fun markAsWatched(savedVisualMedia: SavedVisualMedia)
    suspend fun removeFromWishlist(savedVisualMedia: SavedVisualMedia)
    suspend fun unmarkAsWatched(savedVisualMedia: SavedVisualMedia)
}

class OfflineSavedVisualMediaRepository(
    private val savedVisualMediaDao: SavedVisualMediaDao
) : SavedVisualMediaRepository {
    override fun getWishlist(): Flow<List<SavedVisualMedia>> {
        return savedVisualMediaDao.getWishlist()
    }

    override fun getWatched(): Flow<List<SavedVisualMedia>> {
        return savedVisualMediaDao.getWatched()
    }

    override suspend fun insertOrReplace(savedVisualMedia: SavedVisualMedia) {
        savedVisualMediaDao.insertOrReplace(savedVisualMedia)
    }

    override suspend fun addToWishList(savedVisualMedia: SavedVisualMedia) {
        savedVisualMediaDao.addToWishList(savedVisualMedia)
    }

    override suspend fun markAsWatched(savedVisualMedia: SavedVisualMedia) {
        savedVisualMediaDao.markAsWatched(savedVisualMedia)
    }

    override suspend fun removeFromWishlist(savedVisualMedia: SavedVisualMedia) {
        savedVisualMediaDao.removeFromWishlist(savedVisualMedia)
    }

    override suspend fun unmarkAsWatched(savedVisualMedia: SavedVisualMedia) {
        savedVisualMediaDao.unmarkAsWatched(savedVisualMedia)
    }
}


