package com.example.watchedthat.data

import com.example.watchedthat.model.VisualMedia
import com.example.watchedthat.network.MoviesApiService
import com.example.watchedthat.network.TvShowsApiService

interface VisualMediaRepository {
    suspend fun getTrending(): List<VisualMedia>
}

class NetworkVisualMediaRepository(
    private val moviesApiService: MoviesApiService,
    private val tvShowsApiService: TvShowsApiService
) : VisualMediaRepository {
    override suspend fun getTrending(): List<VisualMedia> {
        val results = moviesApiService.getTrendingMovies().results +
            tvShowsApiService.getTrendingTvShows().results
        return results.sortedByDescending { it.popularity }
    }
}
