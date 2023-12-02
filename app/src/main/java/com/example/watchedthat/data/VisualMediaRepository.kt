package com.example.watchedthat.data

import com.example.watchedthat.model.visualmedia.VisualMedia
import com.example.watchedthat.model.visualmedia.TrendingTimeWindow
import com.example.watchedthat.network.VisualMediaApiService

interface VisualMediaRepository {
    suspend fun search(query: String): List<VisualMedia>
    suspend fun getMoreSearchResults(): List<VisualMedia>
    suspend fun getTrending(
        timeWindow: TrendingTimeWindow = TrendingTimeWindow.TODAY
    ): List<VisualMedia>
    suspend fun getMoreTrending(): List<VisualMedia>
}

class NetworkVisualMediaRepository(
    private val visualMediaApiService: VisualMediaApiService
) : VisualMediaRepository {
    private var currentPage = 1
    private var currentTimeWindow: TrendingTimeWindow? = null
    private var currentSearch: String? = null

    override suspend fun search(query: String): List<VisualMedia> {
        currentSearch = query
        currentPage = 1
        return visualMediaApiService.search(query).results
    }

    override suspend fun getMoreSearchResults(): List<VisualMedia> {
        return visualMediaApiService.search(currentSearch!!, ++currentPage).results
    }

    override suspend fun getTrending(timeWindow: TrendingTimeWindow): List<VisualMedia> {
        currentTimeWindow = timeWindow
        currentPage = 1
        return getTrendingVisualMedia(timeWindow)
    }

    override suspend fun getMoreTrending(): List<VisualMedia> {
        return getTrendingVisualMedia(currentTimeWindow!!, ++currentPage)
    }

    private suspend fun getTrendingVisualMedia(
        timeWindow: TrendingTimeWindow,
        page: Int? = null
    ): List<VisualMedia> {
        return visualMediaApiService.getTrending(timeWindow.value, page).results
    }
}
