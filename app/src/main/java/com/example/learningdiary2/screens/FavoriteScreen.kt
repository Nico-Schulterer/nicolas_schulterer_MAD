package com.example.learningdiary2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.learningdiary2.tools.SimpleAppBar
import com.example.learningdiary2.viewModels.MoviesViewModel

@Composable
fun FavoriteScreen (navController: NavController, viewModel: MoviesViewModel) {
    Column {
        SimpleAppBar(navController = navController, movieTitle = "Favorites")
        MyList(navController = navController, movies = viewModel.favoriteMovieList, viewModel = viewModel)
    }
}
