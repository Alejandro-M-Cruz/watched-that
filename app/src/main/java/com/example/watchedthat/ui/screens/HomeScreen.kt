package com.example.watchedthat.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
    movieDiscoveryState: MovieDiscoveryState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(text = "Home Screen")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(movieDiscoveryState = MovieDiscoveryState.Loading, retryAction = {})
}
