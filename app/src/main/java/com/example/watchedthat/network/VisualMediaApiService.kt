package com.example.watchedthat.network

import retrofit2.http.GET
import retrofit2.http.Query

interface VisualMediaApiService {
    @GET("search/multi?include_adult=false")
    suspend fun search(@Query("query") query: String): VisualMediaPagedResponse

}