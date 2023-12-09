package com.example.watchedthat.network

import androidx.test.core.app.ApplicationProvider
import com.example.watchedthat.WatchedThatApplication
import com.example.watchedthat.data.GenresRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GenresRepositoryTest {
    private lateinit var repository: GenresRepository

    @Before
    fun loadApplication() {
        val application: WatchedThatApplication = ApplicationProvider.getApplicationContext()
        repository = application.container.genresRepository
    }

    @Test
    fun getMovieGenres_returnsMovieGenres() = runTest {
        val movieGenres = repository.getMovieGenres()
        assert(movieGenres.isNotEmpty())
    }

    @Test
    fun getTvShowGenres_returnsTvShowGenres() = runTest {
        val tvShowGenres = repository.getTvShowGenres()
        assert(tvShowGenres.isNotEmpty())
    }

    @Test
    fun getAllGenres_returnsAllGenresSortedByName() = runTest {
        val movieGenres = repository.getMovieGenres()
        val tvShowGenres = repository.getTvShowGenres()
        val allGenres = repository.getAllGenres()
        assert(allGenres.containsAll(movieGenres))
        assert(allGenres.containsAll(tvShowGenres))
        assert(allGenres == allGenres.sortedBy { it.name })
    }
}
