package com.example.watchedthat.data

import android.content.Context
import com.example.watchedthat.BuildConfig
import com.example.watchedthat.Constants
import com.example.watchedthat.db.AppDatabase
import com.example.watchedthat.network.VisualMediaApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val visualMediaRepository: VisualMediaRepository
    val savedVisualMediaRepository: SavedVisualMediaRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
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

    override val savedVisualMediaRepository: SavedVisualMediaRepository by lazy {
        OfflineSavedVisualMediaRepository(AppDatabase.getInstance(context).savedVisualMediaDao())
    }
}
