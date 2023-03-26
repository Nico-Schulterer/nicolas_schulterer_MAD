package com.example.learningdiary2.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.learningdiary2.models.Movie
import com.example.learningdiary2.models.getMovies
import com.example.learningdiary2.tools.MovieRow
import com.example.learningdiary2.ui.theme.Purple500

@Composable
fun HomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            TopAppBar(navController = navController)
            MyList(navController = navController)
        }
    }
}
@Composable
fun MyList(movies: List<Movie> = getMovies(), navController: NavController){

    LazyColumn(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)){

        items(movies) {movie ->
            MovieRow(movie = movie) {movieID ->
                Log.d("MainContent", "My Callback value : $movieID")
                navController.navigate(Screen.Detail.withArgs(movieID))
            }
        }
    }
}
@Composable
fun TopAppBar(navController: NavController) {
    val items = listOf("Favorites")
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
                .background(Purple500)) {
            items.forEachIndexed { index, s ->  
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    menuIcon = Icons.Default.MoreVert
                }) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(Screen.Favorite.route) },
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Open menu",
                            modifier = Modifier
                                .padding(10.dp),
                            tint = Color.White
                        )
                        Text(text = s, color = Color.White, textAlign = TextAlign.Right)
                    }

                }
            }
        }
    }
}
