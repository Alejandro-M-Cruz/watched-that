package com.example.watchedthat.network

import com.example.watchedthat.model.Movie
import com.example.watchedthat.unused.GenresResponse
import retrofit2.http.GET

interface MoviesApiService {
    @GET("discover/movie?include_adult=false")
    suspend fun getTrendingMovies(): VisualMediaPagedResponse

    @GET("genre/movie/list")
    suspend fun getAllMovieGenres(): GenresResponse

    /*@GET("/movie/{id}?append_to_response=videos")
    suspend fun getMovieDetails(@Path("id") movieId: Int): MovieDetails

    @GET("/search/movie?include_adult=false")
    suspend fun searchMovie(@Query("query") query: String): PagedResponse<Movie>*/
}