package com.example.learningdiary.tools

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.learningdiary.models.Movie
import com.example.lectureexamples.R

@Composable
fun MovieRow(movie: Movie, onImageClick: (String) -> Unit, onFavoriteClick: () -> Unit) {

    var expanded by remember {
        mutableStateOf(false)
    }

    val rotationState by animateFloatAsState(targetValue = if (expanded) -180f else 0f)

    var isFavorite by remember {
        mutableStateOf(movie.isFavorite)
    }

    var _movie by remember {
        mutableStateOf(movie)
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp)
        .animateContentSize(tween(delayMillis = 300, easing = LinearOutSlowInEasing)),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 10.dp,
    ) {
        Column {
            Box(modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clickable { onImageClick(movie.id) }
            ) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = if (movie.images.isNotEmpty()) movie.images[0] else R.drawable.imageerror)
                        .apply(block = fun ImageRequest.Builder.() {
                            placeholder(R.drawable.imageplaceholder)
                            error(R.drawable.imageerror)
                            crossfade(300)
                        }).build()
                )
                Image(
                    painter = painter,
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(onClick = {
                        isFavorite = _movie.isFavorite
                        onFavoriteClick()
                    }) {
                        Icon (
                            tint = if (_movie.id == movie.id) (if (isFavorite) Color.Red else MaterialTheme.colors.secondary) else Color.Red,
                            imageVector = if (_movie.id == movie.id) (if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder) else Icons.Default.Favorite,
                            contentDescription = "Add to favorites"
                        )
                    }
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(movie.title, style = MaterialTheme.typography.h6)
                IconButton(modifier = Modifier
                    .rotate(rotationState), onClick = {
                    expanded = !expanded
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Show details")
                }
            }
            if (expanded) {
                Text (
                    text = "Director: ${movie.director} \n" +
                            "Released: ${movie.year} \n" +
                            "Genre: ${movie.genre} \n" +
                            "Actors: ${movie.actors} \n" +
                            "Rating: ${movie.rating} \n",
                    modifier = Modifier
                        .padding(10.dp),
                    style = MaterialTheme.typography.subtitle1
                )

                HorizontalDivider(1.dp, Color.Black)

                Text(text = "Plot: ${movie.plot}",
                    modifier = Modifier
                        .padding(10.dp),
                    style = MaterialTheme.typography.subtitle1)
            }
        }
    }
}