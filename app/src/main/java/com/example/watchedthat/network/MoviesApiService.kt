package com.example.watchedthat.network

import retrofit2.http.GET

interface MoviesApiService {
    @GET("discover/movie?include_adult=false")
    suspend fun getTrendingMovies(): VisualMediaPagedResponse
}