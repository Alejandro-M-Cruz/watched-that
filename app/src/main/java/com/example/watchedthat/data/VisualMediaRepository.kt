package com.example.watchedthat.data

import com.example.watchedthat.model.VisualMedia
import com.example.watchedthat.network.MoviesApiService
import com.example.watchedthat.network.TvShowsApiService
import com.example.watchedthat.network.VisualMediaApiService

interface VisualMediaRepository {
    suspend fun getTrending(): List<VisualMedia>
    suspend fun search(query: String): List<VisualMedia>
}

class NetworkVisualMediaRepository(
    private val moviesApiService: MoviesApiService,
    private val tvShowsApiService: TvShowsApiService,
    private val visualMediaApiService: VisualMediaApiService
) : VisualMediaRepository {
    override suspend fun getTrending(): List<VisualMedia> {
        val results = moviesApiService.getTrendingMovies().results +
            tvShowsApiService.getTrendingTvShows().results
        return results.sortedByDescending { it.popularity }
    }

    override suspend fun search(query: String): List<VisualMedia> {
        return visualMediaApiService.search(query).results
    }
}
