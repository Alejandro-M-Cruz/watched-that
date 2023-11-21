package com.example.watchedthat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watchedthat.R
import androidx.compose.material3.Button

/*--------------------ORIGINAL-----------------
@Composable
fun HomeScreen(
    movieDiscoveryState: MovieDiscoveryState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(text = "Home Screen")
}
---------------------------------*/
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    DisplayImages()
    GreetingText(message = "WatchedThat")
    //HomeScreen(movieDiscoveryState = MovieDiscoveryState.Loading, retryAction = {})
}

@Composable
fun HomeScreen(
    visualMediaRetrievalState: VisualMediaRetrievalState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    DisplayImages()
    GreetingText(message = "WatchedThat")
}


@Composable
fun GreetingImage(){
    Image(
        painter = painterResource(R.drawable.testimage),
        contentDescription = null,
        modifier = Modifier
            .padding(16.dp)
            .height(200.dp)
            .width(200.dp)
    )
}


@Composable
fun GreetingText(message: String, modifier: Modifier = Modifier) {
    Column ( verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()

    ){
        GreetingImage()
        Text(
            text = message,
            fontSize = 50.sp,
            modifier = Modifier
        )

            Button(onClick = {

                // --------------Go to VisualMediaList

            }) {
                Text("Go")
            }
    }
}

@Composable
fun DisplayImages(modifier: Modifier = Modifier){
    val imageGit = painterResource(R.drawable.git_icon)
    val imageMail = painterResource(R.drawable.email_icon)

    Image(
        painter = painterResource(R.drawable.home_wallpaper),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        GreetingIcon(imageGit, "PAMN Project")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        GreetingIcon(imageMail, "@ulpgc")
    }
}



@Composable
fun GreetingIcon(image: Painter, message: String){
    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier
            .padding(16.dp)
            .height(60.dp)
            .width(60.dp)
    )
    Text(
        text = message,
        fontSize = 15.sp,
        modifier = Modifier
    )
}

