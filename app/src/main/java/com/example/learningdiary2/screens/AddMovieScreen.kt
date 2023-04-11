package com.example.learningdiary2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.learningdiary2.models.Genre
import com.example.learningdiary2.models.ListItemSelectable
import com.example.learningdiary2.tools.SimpleAppBar
import com.example.learningdiary2.viewModels.MoviesViewModel
import com.example.lectureexamples.R

@Composable
fun AddMovieScreen(navController: NavController, viewModel: MoviesViewModel){

    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState,
    topBar = {
        SimpleAppBar(navController = navController, movieTitle = "Add a Movie")
    },) { padding ->
        MainContent(Modifier.padding(padding), viewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(modifier: Modifier, viewModel: MoviesViewModel) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            val genres = Genre.values().toList()

            var genreItems by remember {
                mutableStateOf(
                genres.map { genre ->
                    ListItemSelectable(
                        title = genre.toString(),
                        isSelected = false
                        )
                    }
                )
            }

            OutlinedTextField(value = viewModel.title.value,
                onValueChange = {
                            viewModel.title.value = it
                            viewModel.validateTitle()
                                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text(stringResource(id = R.string.enter_movie_title))
                },
                isError = viewModel.isTitleValid
            )

            OutlinedTextField(value = viewModel.year.value,
                onValueChange = {
                    viewModel.year.value = it
                    viewModel.validateYear()
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(stringResource(id = R.string.enter_movie_year))
                },
                singleLine = true,
                isError = viewModel.isYearValid
            )
            if (!viewModel.isYearValid) {
                Warning(message = "Year is required!")
            }

            Text(
                text = stringResource(R.string.select_genres),
                modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Start
            )

            LazyHorizontalGrid(
                modifier = Modifier.height(100.dp),
                rows = GridCells.Fixed(3)
            ){
                items(viewModel.genreItems.value) { genreItem ->
                    Chip(
                        modifier = Modifier.padding(2.dp),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (genreItem.isSelected)
                                colorResource(id = R.color.purple_500)
                            else
                                colorResource(id = R.color.white)
                        ),
                        onClick = {
                            viewModel.genreItems.value = genreItems.map {
                                if (it.title == genreItem.title) {
                                    genreItem.copy(isSelected = !genreItem.isSelected)
                                } else {
                                    it
                                }
                            }
                            viewModel.validateGenres()
                        }
                    ) {
                        Text(text = genreItem.title)
                    }
                }
            }

            if (!viewModel.isGenresValid) {
                Warning(message = "At least one genre is required!")
            }

            OutlinedTextField(value = viewModel.director.value,
                onValueChange = {
                    viewModel.director.value = it
                    viewModel.validateDirector()
                                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(stringResource(id = R.string.enter_director))
                },
                singleLine = true,
                isError = viewModel.isDirectorValid
            )

            if (!viewModel.isDirectorValid) {
                Warning(message = "Director is required!")
            }

            OutlinedTextField(value = viewModel.actors.value,
                onValueChange = {
                    viewModel.actors.value = it
                    viewModel.validateActors()
                                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(stringResource(id = R.string.enter_actors))
                },
                isError = viewModel.isActorsValid
            )

            if (!viewModel.isActorsValid) {
                Warning(message = "At least one actor is required!")
            }

            OutlinedTextField(value = viewModel.plot.value,
                onValueChange = {viewModel.plot.value = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                label = {
                     Text(textAlign = TextAlign.Start, text = stringResource(R.string.enter_plot))
                },
                singleLine = true,
                isError = false
            )

            OutlinedTextField(value = viewModel.rating.value,
                onValueChange = {
                                viewModel.rating.value = if(it.startsWith("0")) "" else it
                                viewModel.validateRating()
                                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(stringResource(id = R.string.enter_rating))
                },
                singleLine = true,
                isError = viewModel.isRatingValid
            )

            if (!viewModel.isRatingValid) {
                Warning(message = "Rating in form of a number required")
            }
            
            Button(
                onClick = { viewModel.addMovie() },
                enabled = viewModel.isAddButtonEnabled.value) {
                Text(text = stringResource(id = R.string.add))
            }
        }
    }
}

@Composable
private fun Warning(message: String) {
    Row(modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp)) {
        Icon(
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp),
            imageVector = Icons.Default.Warning,
            contentDescription = "Error Icon",
            tint = Color(255, 200, 0)
        )
    }
}
