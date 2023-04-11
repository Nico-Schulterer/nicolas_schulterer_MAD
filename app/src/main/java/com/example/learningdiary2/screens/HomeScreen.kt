package com.example.learningdiary2.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.learningdiary2.models.DropDownItem
import com.example.learningdiary2.models.Movie
import com.example.learningdiary2.tools.MovieRow
import com.example.learningdiary2.ui.theme.Purple500
import com.example.learningdiary2.viewModels.MoviesViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: MoviesViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            TopAppBar(navController = navController)
            MyList(navController = navController, movies = viewModel.movieList, viewModel = viewModel)
        }
    }
}
@Composable
fun MyList(movies: List<Movie>, navController: NavController, viewModel: MoviesViewModel){

    LazyColumn(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)){

        items(movies) {movie ->
            MovieRow(
                movie = movie,
                onImageClick = {movieID ->
                navController.navigate(Screen.Detail.withArgs(movieID))
            },
                onFavoriteClick = {
                    viewModel.toggleFavorite(movie)
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
