package com.example.learningdiary.screens

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
import com.example.learningdiary.tools.HorizontalDivider
import com.example.learningdiary.tools.MovieImageSlider
import com.example.learningdiary.tools.MovieRow
import com.example.learningdiary.tools.SimpleAppBar
import com.example.learningdiary.viewModels.DetailScreenViewModel
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@Composable
fun DetailScreen(navController: NavController, viewModel: DetailScreenViewModel, movieID: String?) {

    val coroutineScope = rememberCoroutineScope()

    var movie = movieID?.let { viewModel.getMovieByID(it) }
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            SimpleAppBar(navController, movie?.title)
            movie?.let {
                MovieRow (
                    movie = movie,
                    onImageClick = { movieID -> println("movie: $movieID") },
                    onFavoriteClick = {
                        coroutineScope.launch {
                            viewModel.changeFavorite(movie.id)
                        }
                    }
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