package com.example.learningdiary2

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learningdiary2.screens.DetailScreen
import com.example.learningdiary2.screens.HomeScreen
import com.example.learningdiary2.screens.Screens

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