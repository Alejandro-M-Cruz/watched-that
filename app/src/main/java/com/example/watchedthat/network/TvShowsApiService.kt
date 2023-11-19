package com.example.watchedthat.network

import com.example.watchedthat.model.TvShow
import com.example.watchedthat.unused.GenresResponse
import retrofit2.http.GET

interface TvShowsApiService {
    @GET("trending/tv/day")
    suspend fun getTrendingTvShows(): VisualMediaPagedResponse

    @GET("genre/tv/list")
    suspend fun getAllTvShowGenres(): GenresResponse

    /*@GET("/tv/{id}?append_to_response=videos")
    suspend fun getTvShowDetails(@Path("id") id: Int): TvShowDetails

    @GET("/search/tv?include_adult=false")
    suspend fun searchTvShow(@Query("query") query: String): PagedResponse<Movie>*/
}