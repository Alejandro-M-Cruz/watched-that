package com.example.watchedthat.network

import androidx.test.core.app.ApplicationProvider
import com.example.watchedthat.WatchedThatApplication
import com.example.watchedthat.data.VisualMediaRepository
import com.example.watchedthat.model.MediaType
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class VisualMediaRepositoryTest {
    private lateinit var repository: VisualMediaRepository

    @Before
    fun loadApplication() {
        val application: WatchedThatApplication = ApplicationProvider.getApplicationContext()
        repository = application.container.visualMediaRepository
    }

    @Test
    fun search_returnsListOfVisualMedia() = runTest {
        val results = repository.search("Game of Thrones")
        assert(results.any { it.mediaType == MediaType.TV_SHOW })
    }

    @Test
    fun getTrending_returns20TrendingMoviesAnd20TrendingTvShows() = runTest {
        val results = repository.getTrending()
        assert(results.count { it.mediaType == MediaType.TV_SHOW } == 20)
        assert(results.count { it.mediaType == MediaType.MOVIE } == 20)
    }

    @Test
    fun getAllGenres_returnsListOfGenres() = runTest {
        val results = repository.getAllGenres()
        assert(results.isNotEmpty())
    }
}
