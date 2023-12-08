package com.example.watchedthat.network

import com.example.watchedthat.model.details.MovieDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailsApiService {
    @GET("movie/{movie_id}?append_to_response=videos&language=en-US")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetails
}