package com.example.watchedthat.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.watchedthat.BuildConfig
import com.example.watchedthat.R
import com.example.watchedthat.fake.FakeDataSource
import com.example.watchedthat.model.visualmedia.SavedVisualMedia
import com.example.watchedthat.model.visualmedia.VisualMedia

@Composable
fun VisualMediaGrid(
    visualMediaList: List<VisualMedia>,
    modifier: Modifier = Modifier,
    onNavigateToDetails: (VisualMedia) -> Unit,
    onEndReached: () -> Unit = {},
    wishlistButtonOnClick: ((VisualMedia) -> Unit)? = null,
    watchedListButtonOnClick: ((VisualMedia) -> Unit)? = null
) {
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        contentPadding = PaddingValues(4.dp),
        state = gridState,
        modifier = modifier.fillMaxSize()
    ) {
        items(items = visualMediaList, key = { listOf(it.id, it.mediaType.value)}) {
            if (it is SavedVisualMedia) {
                SavedVisualMediaCard(
                    savedVisualMedia = it,
                    wishlistButtonOnClick = wishlistButtonOnClick!!,
                    watchedListButtonOnClick = watchedListButtonOnClick!!,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    onNavigateToDetails = onNavigateToDetails
                )
            } else {
                VisualMediaCard(
                    visualMedia = it,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    onNavigateToDetails = onNavigateToDetails
                )
            }
        }
    }
    val lastVisibleItemIndex by remember {
        derivedStateOf {
            gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        }
    }
    if (lastVisibleItemIndex > 0 && lastVisibleItemIndex == visualMediaList.lastIndex) {
        onEndReached()
    }
}

@Composable
fun SavedVisualMediaCard(
    savedVisualMedia: SavedVisualMedia,
    wishlistButtonOnClick: (VisualMedia) -> Unit,
    watchedListButtonOnClick: (VisualMedia) -> Unit,
    onNavigateToDetails: (VisualMedia) -> Unit,
    modifier: Modifier = Modifier
) {
    VisualMediaCard(
        visualMedia = savedVisualMedia,
        onNavigateToDetails = onNavigateToDetails,
        modifier = modifier,
        actions = {
            WishlistButton(
                visualMedia = savedVisualMedia,
                onClick = wishlistButtonOnClick
            )
            WatchedButton(
                visualMedia = savedVisualMedia,
                onClick = watchedListButtonOnClick
            )
        }
    )
}


@Composable
fun VisualMediaCard(
    visualMedia: VisualMedia,
    modifier: Modifier = Modifier,
    onNavigateToDetails: (VisualMedia) -> Unit,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
    ) {
        IconButton(
            onClick = { onNavigateToDetails(visualMedia) },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            VisualMediaImage(
                imageUrl = visualMedia.backdropUrl,
                title = visualMedia.title,
                modifier = Modifier.fillMaxSize()
            )
        }
        VisualMediaHeader(
            title = visualMedia.title,
            rating = visualMedia.rating,
            ratingCount = visualMedia.ratingCount,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Text(text = visualMedia.mediaType.displayName, modifier = Modifier.padding(8.dp))
            Text(text = visualMedia.releaseDate, modifier = Modifier.padding(8.dp))
        }
        if (actions == null) {
            Spacer(modifier = Modifier.height(4.dp))
        } else {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
            ) {
                actions()
            }
        }
    }
}

@Composable
fun VisualMediaHeader(
    title: String,
    rating: Float,
    ratingCount: Int,
    modifier: Modifier = Modifier,
    titleFontSize: TextUnit = 20.sp
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        VisualMediaTitle(
            title = title,
            fontSize = titleFontSize,
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                .weight(2f)
        )
        VisualMediaRating(
            rating = rating,
            ratingCount = ratingCount,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 4.dp)
                .weight(1f)
        )
    }
}

@Composable
fun VisualMediaTitle(title: String, modifier: Modifier = Modifier, fontSize: TextUnit = 20.sp) {
    Text(
        text = title,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun VisualMediaRating(rating: Float, ratingCount: Int, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row {
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = colorResource(R.color.star_yellow)
            )
            Text(
                text = "%.1f".format(rating),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = "(${ratingCount})",
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
fun VisualMediaImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale? = null,
    title: String? = null
) {
    val imageRequest = ImageRequest
        .Builder(LocalContext.current)
        .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_API_TOKEN}")
        .data(imageUrl)
        .crossfade(true)
        .build()
    AsyncImage(
        model = imageRequest,
        contentDescription = "Backdrop image for ${title ?: "a movie or TV show"}",
        placeholder = painterResource(id = R.drawable.loading_img),
        error = painterResource(id = R.drawable.ic_broken_image),
        contentScale = contentScale ?: ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
fun WatchedButton(
    visualMedia: VisualMedia,
    onClick: (VisualMedia) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = { onClick(visualMedia) }, modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Button to indicate that a movie or TV show has been watched"
        )
    }
}

@Composable
fun WishlistButton(
    visualMedia: VisualMedia,
    onClick: (VisualMedia) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = { onClick(visualMedia) }, modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Button to add a movie or TV show to the wishlist"
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VisualMediaImagePreview() {
    VisualMediaImage(FakeDataSource.movieWithImages.backdropUrl)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VisualMediaCardPreview() {
    VisualMediaCard(visualMedia = FakeDataSource.movieWithImages, onNavigateToDetails = {})
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VisualMediaListPreview() {
    VisualMediaGrid(
        visualMediaList = FakeDataSource.visualMediaWithImages,
        onNavigateToDetails = { }
    )
}
