package com.example.watchedthat.data

import com.example.watchedthat.BuildConfig
import com.example.watchedthat.Constants
import com.example.watchedthat.network.MoviesApiService
import com.example.watchedthat.network.TvShowsApiService
import com.example.watchedthat.network.VisualMediaApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val visualMediaRepository: VisualMediaRepository
}

class DefaultAppContainer : AppContainer {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BaseApiUrl)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(
            OkHttpClient.Builder().addInterceptor {
                val request = it.request()
                val headersWithAuth = request.headers
                    .newBuilder()
                    .add("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
                    .build()
                it.proceed(request.newBuilder().headers(headersWithAuth).build())
            }.build()
        )
        .build()

    private val moviesApiService: MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }

    private val tvShowsApiService: TvShowsApiService by lazy {
        retrofit.create(TvShowsApiService::class.java)
    }

    private val visualMediaApiService: VisualMediaApiService by lazy {
        retrofit.create(VisualMediaApiService::class.java)
    }

    override val visualMediaRepository: VisualMediaRepository by lazy {
        NetworkVisualMediaRepository(moviesApiService, tvShowsApiService, visualMediaApiService)
    }
}
