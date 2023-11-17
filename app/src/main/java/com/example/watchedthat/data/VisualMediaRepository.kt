package com.example.watchedthat.data

import com.example.watchedthat.model.VisualMedia
import com.example.watchedthat.network.MoviesApiService
import com.example.watchedthat.network.TvShowsApiService
import com.example.watchedthat.network.VisualMediaApiService
import com.example.watchedthat.unused.Genre

interface VisualMediaRepository {
    suspend fun getTrending(): List<VisualMedia>
    suspend fun search(query: String): List<VisualMedia>
    suspend fun getAllGenres(): List<Genre>
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

    override suspend fun getAllGenres(): List<Genre> {
        return (moviesApiService.getAllMovieGenres().genres +
            tvShowsApiService.getAllTvShowGenres().genres).distinct()
    }
}
