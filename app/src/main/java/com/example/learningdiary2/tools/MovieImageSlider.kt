package com.example.learningdiary2.tools

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.learningdiary2.models.Movie
import com.example.lectureexamples.R

@Composable
fun MovieImageSlider(movie: Movie) {
    LazyRow {
        items(movie.images) { movieImage ->
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = movieImage).apply(block = fun ImageRequest.Builder.() {
                    placeholder(R.drawable.imageplaceholder)
                    error(R.drawable.imageerror)
                    crossfade(300)
                }).build()
            )
            ImageCard (painter = painter)
        }
    }
}
@Composable
private fun ImageCard(painter: Painter) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp) {
        Box(modifier = Modifier
            .height(200.dp)
            .width(400.dp)) {
            Image(painter = painter, contentDescription = "Movie Image", contentScale = ContentScale.Crop)
        }
    }
}