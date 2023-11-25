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
import com.example.watchedthat.data.SavedVisualMediaRepository
import com.example.watchedthat.model.SavedVisualMedia
import com.example.watchedthat.model.VisualMedia
import kotlinx.coroutines.launch

class WatchedListViewModel(
    private val savedVisualMediaRepository: SavedVisualMediaRepository
) : ViewModel() {
    var watchedListUiState: SavedVisualMediaUiState by mutableStateOf(
        SavedVisualMediaUiState.Loading
    )
        private set

    fun loadWatchedList() {
        viewModelScope.launch {
            watchedListUiState = SavedVisualMediaUiState.Loading
            watchedListUiState = try {
                val savedVisualMediaList = savedVisualMediaRepository.getWatchedList()
                SavedVisualMediaUiState.Success(savedVisualMediaList)
            } catch (e: Exception) {
                SavedVisualMediaUiState.Error
            }
        }
    }

    fun addToWishlist(savedVisualMedia: VisualMedia) {
        viewModelScope.launch {
            savedVisualMediaRepository.addToWishList(savedVisualMedia as SavedVisualMedia)
        }
    }

    fun removeFromWatched(savedVisualMedia: VisualMedia) {
        viewModelScope.launch {
            savedVisualMediaRepository.removeFromWatchedList(savedVisualMedia as SavedVisualMedia)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WatchedThatApplication
                val savedVisualMediaRepository =
                    application.container.savedVisualMediaRepository
                WatchedListViewModel(savedVisualMediaRepository)
            }
        }
    }
}