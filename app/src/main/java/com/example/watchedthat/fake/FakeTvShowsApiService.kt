package com.example.watchedthat.fake

import com.example.watchedthat.model.TvShow
import com.example.watchedthat.network.PagedResponse
import com.example.watchedthat.network.TvShowsApiService
import com.example.watchedthat.unused.GenresResponse

class FakeTvShowsApiService : TvShowsApiService {
    override suspend fun getTrendingTvShows(): PagedResponse<TvShow> {
        return PagedResponse(
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