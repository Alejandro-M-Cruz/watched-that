package com.example.watchedthat.network

import com.example.watchedthat.data.NetworkVisualMediaRepository
import com.example.watchedthat.fake.FakeDataSource
import com.example.watchedthat.fake.FakeMoviesApiService
import com.example.watchedthat.fake.FakeTvShowsApiService
import com.example.watchedthat.fake.FakeVisualMediaApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkVisualMediaRepositoryTest {
    @Test
    fun search_returnsListOfVisualMedia() = runTest {
        val repository = NetworkVisualMediaRepository(
            visualMediaApiService = FakeVisualMediaApiService(),
            moviesApiService = FakeMoviesApiService(),
            tvShowsApiService = FakeTvShowsApiService()
        )
        val query = "query"

        val results = repository.search(query)

        assertEquals(FakeDataSource.visualMediaList, results)
    }
}