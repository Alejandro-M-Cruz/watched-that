package com.example.watchedthat.network

import com.example.watchedthat.model.visualmedia.VisualMediaPagedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VisualMediaApiService {
    @GET("search/multi?include_adult=false")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): VisualMediaPagedResponse

    @GET("trending/all/{time_window}")
    suspend fun getTrending(
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int = 1
    ): VisualMediaPagedResponse
}