package com.example.watchedthat.data

import com.example.watchedthat.model.genre.Genre
import com.example.watchedthat.network.GenresApiService

interface GenresRepository {
    suspend fun getMovieGenres(): List<Genre>
    suspend fun getTvShowGenres(): List<Genre>
    suspend fun getAllGenres(): List<Genre>
}

class NetworkGenresRepository(
    private val genresApiService: GenresApiService
) : GenresRepository {
    override suspend fun getMovieGenres(): List<Genre> {
        return genresApiService.getMovieGenres().genres
    }

    override suspend fun getTvShowGenres(): List<Genre> {
        return genresApiService.getTvGenres().genres
    }

    override suspend fun getAllGenres(): List<Genre> {
        return (getMovieGenres() + getTvShowGenres()).distinctBy { it.id }.sortedBy { it.name }
    }
}
