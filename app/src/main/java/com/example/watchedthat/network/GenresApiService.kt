package com.example.watchedthat.network

import com.example.watchedthat.model.genre.GenresResponse
import retrofit2.http.GET

interface GenresApiService {
    @GET("genre/movie/list")
    suspend fun getMovieGenres(): GenresResponse

    @GET("genre/tv/list")
    suspend fun getTvGenres(): GenresResponse
}