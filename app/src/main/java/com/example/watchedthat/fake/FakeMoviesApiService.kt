package com.example.watchedthat.fake

import com.example.watchedthat.model.Movie
import com.example.watchedthat.network.MoviesApiService
import com.example.watchedthat.network.PagedResponse
import com.example.watchedthat.unused.GenresResponse

class FakeMoviesApiService : MoviesApiService {
    override suspend fun getTrendingMovies(): PagedResponse<Movie> {
        return PagedResponse(
            page = 1,
            totalPages = 1,
            totalResults = 1,
            results = FakeDataSource.movieList
        )
    }

    override suspend fun getAllMovieGenres(): GenresResponse {
        return GenresResponse(genres = FakeDataSource.genreList)
    }
}