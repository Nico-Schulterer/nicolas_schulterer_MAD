package com.example.learningdiary2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.learningdiary2.tools.SimpleAppBar

@Composable
fun FavoriteScreen (navController: NavController) {
    Column {
        SimpleAppBar(navController = navController, movieTitle = "Favorites")
        MyList(navController = navController)
    }
}
