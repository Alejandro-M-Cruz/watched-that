package com.example.watchedthat.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

data class TextDetail(val label: String, val value: String)


@Composable
fun TextDetailColumn(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        DetailLabel(text = label, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = value, fontSize = 16.sp)
    }
}

@Composable
fun DetailLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        modifier = modifier
    )
}

@Composable
fun ExternalLink(
    text: String,
    url: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = 16.sp
) {
    val uriHandler = LocalUriHandler.current
    ClickableText(
        text = buildAnnotatedString {
            val linkColor = MaterialTheme.colorScheme.primary
            withStyle(style = ParagraphStyle(textAlign = textAlign)) {
                withStyle(style = SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = fontSize,
                    color = linkColor,
                    textDecoration = TextDecoration.Underline
                )
                ) {
                    append(text)
                }
                addStringAnnotation(
                    tag = "URL",
                    annotation = url,
                    start = 0,
                    end = text.length
                )
            }
        },
        onClick = { uriHandler.openUri(url) },
        modifier = modifier
    )
}

@Composable
fun TrailerVideo(trailerPath: String, lifecycleOwner: LifecycleOwner, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            YouTubePlayerView(context = context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(trailerPath, 0f)
                    }
                })
            }
        }
    )
}


