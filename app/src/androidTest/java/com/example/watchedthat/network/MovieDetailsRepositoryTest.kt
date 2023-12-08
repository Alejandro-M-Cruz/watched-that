package com.example.watchedthat.network

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.example.watchedthat.WatchedThatApplication
import com.example.watchedthat.data.MovieDetailsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieDetailsRepositoryTest {
    private lateinit var repository: MovieDetailsRepository

    @Before
    fun loadApplication() {
        val application: WatchedThatApplication = ApplicationProvider.getApplicationContext()
        repository = application.container.movieDetailsRepository
    }

    @Test
    fun getMovieDetails_returnsMovieDetails() = runTest {
        val movieDetails = repository.getMovieDetails(389)
        Log.d("MovieDetailsRepositoryTest", "movieDetails: $movieDetails")
        assert(movieDetails.title == "12 Angry Men")
    }
}