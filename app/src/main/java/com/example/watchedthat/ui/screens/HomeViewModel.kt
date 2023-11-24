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

sealed interface VisualMediaRetrievalState {
    data class Success(val visualMediaList: List<VisualMedia>) : VisualMediaRetrievalState
    object Loading : VisualMediaRetrievalState
    object Error : VisualMediaRetrievalState
}

class HomeViewModel(private val visualMediaRepository: VisualMediaRepository) : ViewModel() {
    var visualMediaRetrievalState: VisualMediaRetrievalState by mutableStateOf(
        VisualMediaRetrievalState.Loading
    )
        private set

    fun loadTrendingVisualMedia() {
        visualMediaRetrievalState = VisualMediaRetrievalState.Loading
        viewModelScope.launch {
            visualMediaRetrievalState = try {
                VisualMediaRetrievalState.Success(visualMediaRepository.getTrending())
            } catch (e: Exception) {
                VisualMediaRetrievalState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WatchedThatApplication
                val visualMediaRepository = application.container.visualMediaRepository
                HomeViewModel(visualMediaRepository)
            }
        }
    }
}