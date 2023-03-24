package com.example.learningdiary2.screens

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.learningdiary2.models.Movie
import com.example.learningdiary2.models.getMovies
import com.example.learningdiary2.ui.theme.Purple500
import com.example.lectureexamples.R

@Composable
fun HomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            TopAppBar()
            MyList(navController = navController)
        }
    }
}
@Composable
fun MyList(movies: List<Movie> = getMovies(), navController: NavController){

    LazyColumn(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)){

        items(movies) {movie ->
            MovieRow(movie = movie, modifier = Modifier.padding(5.dp)) { movieId ->
                Log.d("MainContent", "My callback value: $movieId")
                navController.navigate(Screens.DetailScreen.name)
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, modifier: Modifier, onItemClick : (String) -> Unit, ) {

    var expanded by remember {
        mutableStateOf(false)
    }

    val rotationState by animateFloatAsState(targetValue = if (expanded) -180f else 0f)


    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp)
        .animateContentSize(tween(delayMillis = 300, easing = LinearOutSlowInEasing)),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 10.dp,
    ) {
        Column {
            Box(modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
            ) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = movie.images[0])
                        .apply(block = fun ImageRequest.Builder.() {
                            placeholder(R.drawable.imageplaceholder)
                            error(R.drawable.imageerror)
                            crossfade(300)
                        }).build()
                )
                Image(
                    painter = painter,
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        tint = MaterialTheme.colors.secondary,
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Add to favorites"
                    )
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(movie.title, style = MaterialTheme.typography.h6)
                IconButton(modifier = Modifier
                    .rotate(rotationState), onClick = {
                        expanded = !expanded
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Show details")
                }
            }
            if (expanded) {
                Text (
                    text = "Director: ${movie.director} \n" +
                            "Released: ${movie.year} \n" +
                            "Genre: ${movie.genre} \n" +
                            "Actors: ${movie.actors} \n" +
                            "Rating: ${movie.rating} \n",
                    modifier = Modifier
                        .padding(10.dp),
                    style = MaterialTheme.typography.subtitle1
                )

                HorizontalDivider(1.dp, Color.Black)

                Text(text = "Plot: ${movie.plot}",
                modifier = Modifier
                    .padding(10.dp),
                style = MaterialTheme.typography.subtitle1)
            }
        }
    }
}

@Composable
fun HorizontalDivider(thickness: Dp, color: Color) {
    Column(modifier = Modifier
        .padding(10.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center) {
        Divider(thickness = thickness, color = color)
    }
}

@Preview
@Composable
fun TopAppBar() {
    var items = listOf("Favorites")
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
