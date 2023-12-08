package com.example.watchedthat.network

import androidx.test.core.app.ApplicationProvider
import com.example.watchedthat.WatchedThatApplication
import com.example.watchedthat.data.TvShowDetailsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TvShowDetailsRepositoryTest {
    private lateinit var repository: TvShowDetailsRepository

    @Before
    fun loadApplication() {
        val application: WatchedThatApplication = ApplicationProvider.getApplicationContext()
        repository = application.container.tvShowDetailsRepository
    }

    @Test
    fun getTvShowDetails_returnsTvShowDetails() = runTest {
        val movieDetails = repository.getTvShowDetails(1399)
        assert(movieDetails.title == "Game of Thrones")
    }
}