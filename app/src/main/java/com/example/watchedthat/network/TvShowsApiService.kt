package com.example.watchedthat.network

import com.example.watchedthat.model.Movie
import com.example.watchedthat.model.MovieDetails
import com.example.watchedthat.model.TvShow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowsApiService {
    @GET("/trending/tv/day")
    suspend fun getTrendingTvShows(): PagedResponse<TvShow>

    @GET("/tv/{id}?append_to_response=videos")
    suspend fun getDetails(@Path("id") id: Int): MovieDetails

    @GET("/search/tv?include_adult=false")
    suspend fun search(@Query("query") query: String): PagedResponse<Movie>

    @GET("/genre/tv/list")
    suspend fun getAllGenres(): GenresResponse
}