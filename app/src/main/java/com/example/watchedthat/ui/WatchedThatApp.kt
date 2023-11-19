package com.example.watchedthat.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watchedthat.ui.screens.HomeScreen
import com.example.watchedthat.ui.screens.HomeViewModel

@Composable
fun WatchedThatApp() {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)

   HomeScreen(homeViewModel.movieDiscoveryState, homeViewModel::discoverMovies)
}