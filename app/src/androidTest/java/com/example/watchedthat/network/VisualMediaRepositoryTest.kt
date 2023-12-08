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
    fun getMoreSearchResults_returnsMoreResults() = runTest {
        val firstPageResults = repository.search("Spielberg")
        val secondPageResults = repository.getMoreSearchResults()
        assert(firstPageResults.none { secondPageResults.contains(it) })
    }

    @Test
    fun getTrending_returnsTrendingVisualMedia() = runTest {
        val results = repository.getTrending()
        assert(results.isNotEmpty())
    }

    @Test
    fun getMoreTrending_returnsMoreTrendingMoviesAndTvShows() = runTest {
        val firstPageResults = repository.getTrending()
        val secondPageResults = repository.getMoreTrending()
        assert(firstPageResults != secondPageResults)
    }
}
