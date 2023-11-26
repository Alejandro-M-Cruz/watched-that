package com.example.watchedthat.ui.screens

import com.example.watchedthat.model.VisualMedia
import kotlinx.coroutines.flow.Flow

sealed interface VisualMediaUiState {
    data class Success(
        val visualMediaList: List<VisualMedia>,
        val resultType: ResultType
    ) : VisualMediaUiState
    object Loading : VisualMediaUiState
    object Error : VisualMediaUiState
}

enum class ResultType {
    Trending,
    Search
}
