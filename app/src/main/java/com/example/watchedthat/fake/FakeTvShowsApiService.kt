package com.example.watchedthat.fake

import com.example.watchedthat.network.VisualMediaPagedResponse
import com.example.watchedthat.network.TvShowsApiService
import com.example.watchedthat.model.GenresResponse

class FakeTvShowsApiService : TvShowsApiService {
    override suspend fun getTrendingTvShows(): VisualMediaPagedResponse {
        return VisualMediaPagedResponse(
            page = 1,
            totalPages = 1,
            totalResults = 1,
            results = FakeDataSource.tvShowList
        )
    }

    override suspend fun getAllTvShowGenres(): GenresResponse {
        return GenresResponse(genres = FakeDataSource.genreList)
    }
}