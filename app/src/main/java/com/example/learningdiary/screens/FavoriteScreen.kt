package com.example.learningdiary.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.learningdiary.tools.SimpleAppBar
import com.example.learningdiary.utils.InjectorUtils
import com.example.learningdiary.viewModels.FavoriteScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen (navController: NavController) {

    val viewModel: FavoriteScreenViewModel = viewModel(
        factory = InjectorUtils.provideFavoriteScreenViewModelFactory(LocalContext.current))

    val favMoviesState = viewModel.favMovieList.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var forceFavorite: Boolean by remember {
        mutableStateOf(false)
    }

    Column {
        SimpleAppBar(navController = navController, movieTitle = "Favorites")
        MyList(movies = favMoviesState, onImageClick = { movieID ->
            navController.navigate(Screen.Detail.withArgs(movieID))
        }, onFavoriteClick = {
            coroutineScope.launch {
                viewModel.changeFavorite(it)
                forceFavorite = !forceFavorite
            }
        })
    }
}
