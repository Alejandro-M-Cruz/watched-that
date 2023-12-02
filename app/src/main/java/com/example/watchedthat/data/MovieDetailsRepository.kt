package com.example.watchedthat.data

import com.example.watchedthat.model.details.MovieDetails
import com.example.watchedthat.network.MovieDetailsApiService

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId: Int): MovieDetails
}

class NetworkMovieDetailsRepository(
    private val movieDetailsApiService: MovieDetailsApiService
) : MovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return movieDetailsApiService.getMovieDetails(movieId)
    }
}
