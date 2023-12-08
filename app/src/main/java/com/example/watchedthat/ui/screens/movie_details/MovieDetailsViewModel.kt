package com.example.watchedthat.ui.screens.movie_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.watchedthat.WatchedThatApplication
import com.example.watchedthat.data.MovieDetailsRepository
import com.example.watchedthat.data.SavedVisualMediaRepository
import com.example.watchedthat.model.details.MovieDetails
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed interface MovieDetailsUiState {
    data class Success(val movieDetails: MovieDetails) : MovieDetailsUiState
    object Error : MovieDetailsUiState
    object Loading : MovieDetailsUiState
}

class MovieDetailsViewModel (
    savedStateHandle: SavedStateHandle,
    private val movieDetailsRepository: MovieDetailsRepository,
    private val savedVisualMediaRepository: SavedVisualMediaRepository
) : ViewModel() {
    var movieDetailsUiState: MovieDetailsUiState by mutableStateOf(MovieDetailsUiState.Loading)
        private set
    private val movieId = savedStateHandle.get<Int>("movie_id")!!
    private val movieDetails
        get() = (movieDetailsUiState as MovieDetailsUiState.Success).movieDetails

    init {
        loadMovieDetails()
    }

    fun loadMovieDetails() {
        viewModelScope.launch {
            movieDetailsUiState = MovieDetailsUiState.Loading
            movieDetailsUiState = try {
                val movieDetails = movieDetailsRepository.getMovieDetails(movieId)
                MovieDetailsUiState.Success(movieDetails = movieDetails)
            } catch (e: Exception) {
                Log.d("MovieDetailsViewModel", "Error loading movie details", e)
                MovieDetailsUiState.Error
            }
        }
    }

    fun addToWishlist() {
        viewModelScope.launch {
            val savedVisualMedia = savedVisualMediaRepository
                .getByIdAndMediaType(movieDetails.id, movieDetails.mediaType).first()
            savedVisualMediaRepository.addToWishList(
                savedVisualMedia ?: movieDetails.toSavedVisualMedia()
            )
        }
    }

    fun addToWatchedList() {
        viewModelScope.launch {
            val savedVisualMedia = savedVisualMediaRepository
                .getByIdAndMediaType(movieDetails.id, movieDetails.mediaType).first()
            savedVisualMediaRepository.addToWatchedList(
                savedVisualMedia ?: movieDetails.toSavedVisualMedia()
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WatchedThatApplication
                val movieDetailsRepository = application.container.movieDetailsRepository
                val savedVisualMediaRepository = application.container.savedVisualMediaRepository
                MovieDetailsViewModel(
                    this.createSavedStateHandle(),
                    movieDetailsRepository,
                    savedVisualMediaRepository
                )
            }
        }
    }
}