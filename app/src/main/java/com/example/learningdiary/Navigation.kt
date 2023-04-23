package com.example.learningdiary

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.learningdiary.screens.*
import com.example.learningdiary.utils.InjectorUtils
import com.example.learningdiary.viewModels.DetailScreenViewModel


const val movieID: String = "moveId"

@Composable
fun Navigation(){

    val navController = rememberNavController()

    val detailScreenViewModel: DetailScreenViewModel = viewModel(
        factory = InjectorUtils.provideDetailScreenViewModelFactory(LocalContext.current)
    )

    NavHost(navController = navController,
    startDestination = Screen.Home.route) {

        composable( route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.Detail.route + "/{$movieID}",
            arguments = listOf(navArgument(movieID) {
                type = NavType.StringType
            })) {
            backStackEntry ->
                DetailScreen(
                    navController = navController,
                    viewModel = detailScreenViewModel,
                    movieID = backStackEntry.arguments?.getString(movieID)
                )
        }

        composable(
            route = Screen.Favorite.route
        ) {
            FavoriteScreen(navController = navController)
        }

        composable(route = Screen.AddMovie.route) {
            AddMovieScreen(navController = navController)
        }

    }
    
}