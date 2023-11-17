package com.example.watchedthat.network

import com.example.watchedthat.model.Movie
import com.example.watchedthat.model.MovieDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("/discover/movie?include_adult=false")
    suspend fun getTrendingMovies(): PagedResponse<Movie>

    @GET("/movie/{id}?append_to_response=videos")
    suspend fun getDetails(@Path("id") id: Int): MovieDetails

    @GET("/search/movie?include_adult=false")
    suspend fun search(@Query("query") query: String): PagedResponse<Movie>

    @GET("/genre/movie/list")
    suspend fun getAllGenres(): GenresResponse
}