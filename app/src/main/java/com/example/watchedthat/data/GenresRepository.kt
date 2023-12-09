package com.example.watchedthat.data

import com.example.watchedthat.db.GenreDao
import com.example.watchedthat.model.genre.Genre
import com.example.watchedthat.network.GenresApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface GenresRepository {
    suspend fun getMovieGenres(): List<Genre>
    suspend fun getTvShowGenres(): List<Genre>
    suspend fun getAllGenres(): List<Genre>
    suspend fun storeGenres(genres: List<Genre>)
    fun getStoredGenres(): Flow<List<Genre>>
}

class DefaultGenresRepository(
    private val genresApiService: GenresApiService,
    private val genreDao: GenreDao
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

    override suspend fun storeGenres(genres: List<Genre>) {
        genreDao.insertAll(genres)
    }

    override fun getStoredGenres(): Flow<List<Genre>> {
        return genreDao.getAll()
    }
}
