package com.example.watchedthat.ui.screens.tv_show_details

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
import com.example.watchedthat.data.SavedVisualMediaRepository
import com.example.watchedthat.data.TvShowDetailsRepository
import com.example.watchedthat.model.details.TvShowDetails
import com.example.watchedthat.model.visual_media.SavedVisualMedia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed interface TvShowDetailsUiState {
    data class Success(
        val tvShowDetails: TvShowDetails,
        val savedVisualMedia: Flow<SavedVisualMedia?>
    ) : TvShowDetailsUiState
    object Error : TvShowDetailsUiState
    object Loading : TvShowDetailsUiState
}

class TvShowDetailsViewModel (
    savedStateHandle: SavedStateHandle,
    private val tvShowDetailsRepository: TvShowDetailsRepository,
    private val savedVisualMediaRepository: SavedVisualMediaRepository
) : ViewModel() {
    var tvShowDetailsUiState: TvShowDetailsUiState by mutableStateOf(TvShowDetailsUiState.Loading)
        private set
    private val tvShowId = savedStateHandle.get<Int>("tv_show_id")!!
    private val tvShowDetails
        get() = (tvShowDetailsUiState as TvShowDetailsUiState.Success).tvShowDetails
    private val savedVisualMediaFlow
        get() = (tvShowDetailsUiState as TvShowDetailsUiState.Success).savedVisualMedia

    init {
        loadUiState()
    }

    fun loadUiState() {
        viewModelScope.launch {
            tvShowDetailsUiState = TvShowDetailsUiState.Loading
            tvShowDetailsUiState = try {
                val tvShowDetails = tvShowDetailsRepository.getTvShowDetails(tvShowId)
                val savedVisualMedia = savedVisualMediaRepository
                    .getByIdAndMediaType(tvShowDetails.id, tvShowDetails.mediaType)
                TvShowDetailsUiState.Success(tvShowDetails = tvShowDetails, savedVisualMedia)
            } catch (e: Exception) {
                TvShowDetailsUiState.Error
            }
        }
    }

    fun addToWishlist() {
        viewModelScope.launch {
            val savedVisualMedia =
                savedVisualMediaFlow.first() ?: tvShowDetails.toSavedVisualMedia()
            if (savedVisualMedia.inWishlist) {
                savedVisualMediaRepository.removeFromWishlist(savedVisualMedia)
            } else {
                savedVisualMediaRepository.addToWishlist(savedVisualMedia)
            }
        }
    }

    fun addToWatchedList() {
        viewModelScope.launch {
            val savedVisualMedia =
                savedVisualMediaFlow.first() ?: tvShowDetails.toSavedVisualMedia()
            if (savedVisualMedia.watched) {
                savedVisualMediaRepository.removeFromWatchedList(savedVisualMedia)
            } else {
                savedVisualMediaRepository.addToWatchedList(savedVisualMedia)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WatchedThatApplication
                val tvShowDetailsRepository = application.container.tvShowDetailsRepository
                val savedVisualMediaRepository = application.container.savedVisualMediaRepository
                TvShowDetailsViewModel(
                    this.createSavedStateHandle(),
                    tvShowDetailsRepository,
                    savedVisualMediaRepository
                )
            }
        }
    }
}