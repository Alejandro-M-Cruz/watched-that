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
import com.example.watchedthat.ui.screens.ResultType
import com.example.watchedthat.ui.screens.VisualMediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class HomeViewModel(
    private val visualMediaRepository: VisualMediaRepository,
    private val savedVisualMediaRepository: SavedVisualMediaRepository
) : ViewModel() {
    var visualMediaUiState: VisualMediaUiState by mutableStateOf(VisualMediaUiState.Loading)
        private set

    fun loadTrendingVisualMedia() {
        viewModelScope.launch {
            visualMediaUiState = VisualMediaUiState.Loading
            visualMediaUiState = try {
                val visualMediaList = visualMediaRepository.getTrending()
                VisualMediaUiState.Success(
                    visualMediaList,
                    ResultType.Trending
                )
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

    fun searchVisualMedia(query: String) {
        viewModelScope.launch {
            visualMediaUiState = VisualMediaUiState.Loading
            visualMediaUiState = try {
                val searchResults = visualMediaRepository.search(query)
                VisualMediaUiState.Success(
                    searchResults,
                    ResultType.Search
                )
            } catch (e: Exception) {
                VisualMediaUiState.Error
            }
        }
    }

    fun loadMoreResults() {
        viewModelScope.launch {
            val uiState = visualMediaUiState as VisualMediaUiState.Success
            visualMediaUiState = try {
                val results = when (uiState.resultType) {
                    ResultType.Trending -> visualMediaRepository.getMoreTrending()
                    ResultType.Search -> visualMediaRepository.getMoreSearchResults()
                }
                VisualMediaUiState.Success(
                    uiState.visualMediaList + results,
                    uiState.resultType
                )
            } catch (e: Exception) {
                VisualMediaUiState.Error
            }
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