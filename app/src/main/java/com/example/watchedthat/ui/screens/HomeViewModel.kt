package com.example.watchedthat.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.watchedthat.WatchedThatApplication
import com.example.watchedthat.data.VisualMediaRepository
import com.example.watchedthat.model.VisualMedia
import kotlinx.coroutines.launch

sealed interface MovieDiscoveryState {
    data class Success(val savedVisualMedia: List<VisualMedia>) : MovieDiscoveryState
    object Loading : MovieDiscoveryState
    object Error : MovieDiscoveryState
}

class HomeViewModel(private val visualMediaRepository: VisualMediaRepository) : ViewModel() {
    var movieDiscoveryState: MovieDiscoveryState by mutableStateOf(MovieDiscoveryState.Loading)
        private set

    init {
        discoverMovies()
    }

    fun discoverMovies() {
        movieDiscoveryState = MovieDiscoveryState.Loading
        viewModelScope.launch {
            movieDiscoveryState = try {
                MovieDiscoveryState.Success(visualMediaRepository.getTrending())
            } catch (e: Exception) {
                MovieDiscoveryState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WatchedThatApplication
                val moviesRepository = application.container.visualMediaRepository
                HomeViewModel(moviesRepository)
            }
        }
    }
}