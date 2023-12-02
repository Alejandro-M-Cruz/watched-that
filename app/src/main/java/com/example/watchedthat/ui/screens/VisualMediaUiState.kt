package com.example.watchedthat.ui.screens

import com.example.watchedthat.model.visualmedia.VisualMedia

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
