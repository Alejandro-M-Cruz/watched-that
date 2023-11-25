package com.example.watchedthat.ui.viewmodel

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
import com.example.watchedthat.data.SavedVisualMediaRepository
import com.example.watchedthat.data.VisualMediaRepository
import com.example.watchedthat.model.VisualMedia
import com.example.watchedthat.ui.screens.VisualMediaUiState
import kotlinx.coroutines.launch

class HomeViewModel(
    private val visualMediaRepository: VisualMediaRepository,
    private val savedVisualMediaRepository: SavedVisualMediaRepository
) : ViewModel() {
    var visualMediaUiState: VisualMediaUiState by mutableStateOf(
        VisualMediaUiState.Loading
    )
        private set

    fun loadTrendingVisualMedia(isFirstRequest: Boolean = true) {
        viewModelScope.launch {
            visualMediaUiState = VisualMediaUiState.Loading
            visualMediaUiState = try {
                val visualMediaList = if (isFirstRequest)
                    visualMediaRepository.getTrending()
                else
                    visualMediaRepository.getMoreTrending()
                println("visualMediaList: $visualMediaList")
                VisualMediaUiState.Success(visualMediaList)
            } catch (e: Exception) {
                VisualMediaUiState.Error
            }
        }
    }

    fun addToWishList(visualMedia: VisualMedia) {
        viewModelScope.launch {
            savedVisualMediaRepository.addToWishList(visualMedia.toSavedVisualMedia())
        }
    }

    fun addToWatchedList(visualMedia: VisualMedia) {
        viewModelScope.launch {
            savedVisualMediaRepository.addToWatchedList(visualMedia.toSavedVisualMedia())
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WatchedThatApplication
                val visualMediaRepository = application.container.visualMediaRepository
                val savedVisualMediaRepository =
                    application.container.savedVisualMediaRepository
                HomeViewModel(visualMediaRepository, savedVisualMediaRepository)
            }
        }
    }
}