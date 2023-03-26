package com.example.learningdiary2.screens

sealed class Screen (val route: String) {
    object Home: Screen("home")
    object Detail: Screen ("detail")
    object Favorite: Screen ("favorite")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}