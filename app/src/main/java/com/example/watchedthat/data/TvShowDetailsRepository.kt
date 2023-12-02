package com.example.watchedthat.data

import com.example.watchedthat.model.details.TvShowDetails
import com.example.watchedthat.network.TvShowDetailsApiService

interface TvShowDetailsRepository {
    suspend fun getTvShowDetails(tvShowId: Int): TvShowDetails
}

class NetworkTvShowDetailsRepository(
    private val tvShowDetailsApiService: TvShowDetailsApiService
) : TvShowDetailsRepository {
    override suspend fun getTvShowDetails(tvShowId: Int): TvShowDetails {
        return tvShowDetailsApiService.getTvShowDetails(tvShowId)
    }
}
