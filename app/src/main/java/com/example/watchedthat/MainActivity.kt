package com.example.watchedthat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.watchedthat.ui.theme.WatchedThatTheme
import com.example.watchedthat.ui.WatchedThatApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           WatchedThatTheme {
                WatchedThatApp()
            }
        }
    }
}
