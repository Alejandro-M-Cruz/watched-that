package com.example.watchedthat.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.watchedthat.WatchedThatApplication
import com.example.watchedthat.data.MovieDetailsRepository
import com.example.watchedthat.model.details.MovieDetails
import kotlinx.coroutines.launch

sealed interface MovieDetailsUiState {
    data class Success(val movieDetails: MovieDetails) : MovieDetailsUiState
    object Error : MovieDetailsUiState
    object Loading : MovieDetailsUiState
}

class MovieDetailsViewModel (
    private val movieDetailsRepository: MovieDetailsRepository
) : ViewModel() {
    var movieDetailsUiState: MovieDetailsUiState by mutableStateOf(MovieDetailsUiState.Loading)
        private set

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            movieDetailsUiState = MovieDetailsUiState.Loading
            movieDetailsUiState = try {
                val movieDetails = movieDetailsRepository.getMovieDetails(movieId)
                MovieDetailsUiState.Success(movieDetails = movieDetails)
            } catch (e: Exception) {
                MovieDetailsUiState.Error
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WatchedThatApplication
                val movieDetailsRepository = application.container.movieDetailsRepository
                MovieDetailsViewModel(movieDetailsRepository)
            }
        }
    }
}