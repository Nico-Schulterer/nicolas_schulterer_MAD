package com.example.lectureexamples

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lectureexamples.screens.DetailScreen
import com.example.lectureexamples.screens.HomeScreen
import com.example.lectureexamples.screens.Screens

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController,
    startDestination = Screens.HomeScreen.name) {
        composable(Screens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
        composable(Screens.DetailScreen.name){
            DetailScreen(navController = navController)
        }
    }
    
}