package com.example.watchedthat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.watchedthat.ui.theme.WatchedThatTheme
// New Imports
import com.example.watchedthat.model.VisualMedia
import androidx.compose.material3.Card
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.LocalContext
import com.example.watchedthat.ui.WatchedThatApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           WatchedThatTheme {
                WatchedThatApp() //ui/theme/WatchedThatApp.kt -> HomeScreen
            }
        }
    }
}

/*------------------------------ORIGINAL------------------------
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WatchedThatTheme {
        Greeting("Android")
    }
}
-----------------------------------------------------------------*/

/*-----------------------------Prove--------------------
@Composable
fun FilmList(){
    Text(
        text = "New Prova"
    )
}


@Preview(showBackground = true)
@Composable
fun NewFunction(){
    WatchedThatTheme {
        FilmList()
    }
}

@Composable
fun VisualMediaList(visualmedia: VisualMedia, modifier: Modifier = Modifier){
    Card(modifier = modifier){
        Column {
            Image(
                painter = painterResource(R.drawable.testimage), //To change
                contentDescription = "null",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop

            )
            Text(
                text = LocalContext.current.getString(visualmedia.id),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )


        }
    }
}


@Preview
@Composable
private fun VisualMediaListPreview() {
    VisualMediaList(Visualmedia("Prova", R.drawable.testimage))
}
*/