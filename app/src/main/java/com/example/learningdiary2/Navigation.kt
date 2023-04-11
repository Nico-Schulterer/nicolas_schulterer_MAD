package com.example.learningdiary2

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.learningdiary2.models.MovieRepo
import com.example.learningdiary2.screens.*
import com.example.learningdiary2.viewModels.MoviesViewModel


const val movieID: String = "moveId"

@Composable
fun Navigation(){

    val navController = rememberNavController()
    val movieRepo: MovieRepo = MovieRepo()
    val moviesViewModel: MoviesViewModel = MoviesViewModel(movieRepo = movieRepo)

    NavHost(navController = navController,
    startDestination = Screen.Home.route) {

        composable( route = Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = moviesViewModel)
        }

        composable(
            route = Screen.Detail.route + "/{$movieID}",
            arguments = listOf(navArgument(movieID) {
                type = NavType.StringType
            })) {
            backStackEntry ->
                DetailScreen(
                    navController = navController,
                    viewModel = moviesViewModel,
                    movieID = backStackEntry.arguments?.getString(movieID)
                )
        }

        composable(
            route = Screen.Favorite.route
        ) {
            FavoriteScreen(navController = navController, viewModel = moviesViewModel)
        }

        composable(route = Screen.AddMovie.route) {
            AddMovieScreen(navController = navController, viewModel = moviesViewModel)
        }

    }
    
}