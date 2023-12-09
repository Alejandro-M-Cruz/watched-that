package com.example.watchedthat.data

import android.content.Context
import com.example.watchedthat.BuildConfig
import com.example.watchedthat.Constants
import com.example.watchedthat.db.AppDatabase
import com.example.watchedthat.network.GenresApiService
import com.example.watchedthat.network.MovieDetailsApiService
import com.example.watchedthat.network.TvShowDetailsApiService
import com.example.watchedthat.network.VisualMediaApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val visualMediaRepository: VisualMediaRepository
    val savedVisualMediaRepository: SavedVisualMediaRepository
    val movieDetailsRepository: MovieDetailsRepository
    val tvShowDetailsRepository: TvShowDetailsRepository
    val genresRepository: GenresRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val appContainerScope = CoroutineScope(Dispatchers.Default)
    private val jsonFormat = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BaseApiUrl)
        .addConverterFactory(jsonFormat.asConverterFactory("application/json".toMediaType()))
        .client(
            OkHttpClient.Builder().addInterceptor {
                val request = it.request()
                val headersWithAuth = request.headers
                    .newBuilder()
                    .add("Authorization", "Bearer ${BuildConfig.TMDB_API_TOKEN}")
                    .build()
                it.proceed(request.newBuilder().headers(headersWithAuth).build())
            }.build()
        )
        .build()

    override val visualMediaRepository: VisualMediaRepository by lazy {
        NetworkVisualMediaRepository(retrofit.create(VisualMediaApiService::class.java))
    }

    override val movieDetailsRepository: MovieDetailsRepository by lazy {
        NetworkMovieDetailsRepository(retrofit.create(MovieDetailsApiService::class.java))
    }

    override val tvShowDetailsRepository: TvShowDetailsRepository by lazy {
        NetworkTvShowDetailsRepository(retrofit.create(TvShowDetailsApiService::class.java))
    }

    override val genresRepository: GenresRepository = DefaultGenresRepository(
        retrofit.create(GenresApiService::class.java),
        AppDatabase.getInstance(context).genreDao()
    ).also {genresRepository -> appContainerScope.launch {
        genresRepository.storeGenres(genresRepository.getAllGenres())
    }}

    override val savedVisualMediaRepository: SavedVisualMediaRepository by lazy {
        OfflineSavedVisualMediaRepository(AppDatabase.getInstance(context).savedVisualMediaDao())
    }
}
