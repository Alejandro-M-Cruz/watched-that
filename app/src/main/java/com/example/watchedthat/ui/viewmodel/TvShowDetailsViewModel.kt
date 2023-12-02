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
import com.example.watchedthat.data.TvShowDetailsRepository
import com.example.watchedthat.model.details.TvShowDetails
import kotlinx.coroutines.launch

sealed interface TvShowDetailsUiState {
    data class Success(val tvShowDetails: TvShowDetails) : TvShowDetailsUiState
    object Error : TvShowDetailsUiState
    object Loading : TvShowDetailsUiState
}

class TvShowDetailsViewModel(
    private val tvShowDetailsRepository: TvShowDetailsRepository
) : ViewModel() {
    var tvShowDetailsUiState: TvShowDetailsUiState by mutableStateOf(TvShowDetailsUiState.Loading)
        private set

    fun loadTvShowDetails(tvShowId: Int) {
        viewModelScope.launch {
            tvShowDetailsUiState = TvShowDetailsUiState.Loading
            tvShowDetailsUiState = try {
                val tvShowDetails = tvShowDetailsRepository.getTvShowDetails(tvShowId)
                TvShowDetailsUiState.Success(tvShowDetails = tvShowDetails)
            } catch (e: Exception) {
                TvShowDetailsUiState.Error
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WatchedThatApplication
                val tvShowDetailsRepository = application.container.tvShowDetailsRepository
                TvShowDetailsViewModel(tvShowDetailsRepository)
            }
        }
    }
}