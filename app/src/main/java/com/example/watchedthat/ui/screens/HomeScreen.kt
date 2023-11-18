package com.example.watchedthat.ui.screens

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watchedthat.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri

/*--------------------ORIGINAL-----------------
@Composable
fun HomeScreenOLD(
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


//-------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    movieDiscoveryState: MovieDiscoveryState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "WatchedThat")
                },

                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Green,
                    titleContentColor = Color.Black,
                ),
            )
        }, content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
               /* Text(
                    text = "Content of the page",
                    fontSize = 30.sp,
                    color = Color.Black
                )*/
               // GreetingText(message = "Go to MovieList", from = "")
                DisplayImages()
            }
        })

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

                // Go to VisualMediaList

            }) {
                Text("Go")
            }



     /*       button.setOnClickListener{
            try{
                val uri = Uri.parse("https://stackoverflow.com/")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            catch(e : ActivityNotFoundException){
            }
        } */
    }
}

@Composable
fun DisplayImages(modifier: Modifier = Modifier){
    val imageGit = painterResource(R.drawable.git_icon)
    val imagePhone = painterResource(R.drawable.phone_icon)
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

