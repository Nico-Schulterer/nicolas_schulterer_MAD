package com.example.learningdiary
import com.example.learningdiary.models.Movie

interface InterfaceMovieRepo {
    fun getMovies() : List<Movie>
    fun addMovie(movie: Movie)
}