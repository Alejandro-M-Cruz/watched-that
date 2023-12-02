package com.example.watchedthat.network

import com.example.watchedthat.model.details.TvShowDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface TvShowDetailsApiService {
    @GET("tv/{tv_show_id}?append_to_response=videos&language=en-US")
    suspend fun getTvShowDetails(@Path("tv_show_id") tvShowId: Int): TvShowDetails
}