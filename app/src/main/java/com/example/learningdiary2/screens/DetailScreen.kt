package com.example.learningdiary2.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.learningdiary2.viewModels.MoviesViewModel
import com.example.learningdiary2.tools.HorizontalDivider
import com.example.learningdiary2.tools.MovieImageSlider
import com.example.learningdiary2.tools.MovieRow
import com.example.learningdiary2.tools.SimpleAppBar

@SuppressLint("SuspiciousIndentation")
@Composable
fun DetailScreen(navController: NavController, viewModel: MoviesViewModel,  movieID: String?) {
    var movie = movieID?.let { viewModel.getMovieByID(it) }
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            SimpleAppBar(navController, movie?.title)
            movie?.let {
                MovieRow (
                    movie = movie,
                    onImageClick = { movieID -> println("movie: $movieID") },
                    onFavoriteClick = { viewModel.toggleFavorite(movie) }
                )
                HorizontalDivider(thickness = 2.dp, color = Color.Black)
                Text(text = "Movie Images",
                    modifier = Modifier
                        .padding(5.dp),
                    style = MaterialTheme.typography.h5)
                MovieImageSlider (movie = movie)
            }
        }
}