package com.example.watchedthat.network

import retrofit2.http.GET

interface TvShowsApiService {
    @GET("trending/tv/day")
    suspend fun getTrendingTvShows(): VisualMediaPagedResponse
}
