package com.example.learningdiary.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.learningdiary.models.DropDownItem
import com.example.learningdiary.models.Movie
import com.example.learningdiary.tools.MovieRow
import com.example.learningdiary.ui.theme.Purple500
import com.example.learningdiary.utils.InjectorUtils
import com.example.learningdiary.viewModels.HomeScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {

    val viewModel: HomeScreenViewModel = viewModel(
        factory = InjectorUtils.provideHomeScreenViewModelFactory(LocalContext.current)
    )

    val moviesState = viewModel.movieList.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            TopAppBar(navController = navController)
            MyList( movies = moviesState, onImageClick = { movieID ->
                navController.navigate(Screen.Detail.withArgs(movieID))
            }, onFavoriteClick = {
                coroutineScope.launch {
                    viewModel.changeFavorite(it)
                }
            }
            )
        }
    }
}
@Composable
fun MyList(movies: State<List<Movie>>, onImageClick: (String) -> Unit, onFavoriteClick: (String) -> Unit){

    LazyColumn(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)){
        items(movies.value) {movie ->
            MovieRow(
                movie = movie,
                onImageClick = {
                    onImageClick.invoke(movie.id)
            },
                onFavoriteClick = {
                    onFavoriteClick.invoke(movie.id)
                }
            )
        }
    }
}
@Composable
fun TopAppBar(navController: NavController) {
    var items = listOf (
        DropDownItem (
            name = "Favorites",
            icon = Icons.Default.Favorite,
            route = Screen.Favorite.route
        ),
        DropDownItem (
            name = "Add Movie",
            icon = Icons.Default.AddCircle,
            route = Screen.AddMovie.route
        )
    )
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    var menuIcon by remember {
        mutableStateOf(Icons.Default.MoreVert)
    }
    Box(modifier = Modifier
        .background(Purple500)
        .fillMaxWidth(),
        contentAlignment = Alignment.TopEnd) {
        Text(
            style = MaterialTheme.typography.h6,
            text = "Movies",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(20.dp, 10.dp),
            color = Color.White
        )
        IconButton(onClick = {
            expanded = true
            menuIcon = Icons.Default.MoreVert
        }) {
            Icon(
                imageVector = menuIcon,
                contentDescription = "Open menu",
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
            expanded = false
            menuIcon = Icons.Default.MoreVert
        },
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple500)
                .animateContentSize(animationSpec = tween(easing = LinearOutSlowInEasing))) {
            items.forEachIndexed { index, item ->
                TopAppBarItem(navController = navController, item = item) {
                    selectedIndex = index
                    expanded = false
                    menuIcon = Icons.Default.Menu
                }
            }
        }
    }
}
@Composable
private fun TopAppBarItem(navController: NavController, item: DropDownItem, onMenuClick: () -> Unit) {
    DropdownMenuItem (onClick = { onMenuClick() }) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(item.route)
                },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text (
                text = item.name,
                color = Color.White,
                textAlign = TextAlign.Right
            )

            Icon (
                imageVector = item.icon,
                contentDescription = "Open Options",
                modifier = Modifier.padding(10.dp),
                tint = Color.White
            )
        }
    }
}
