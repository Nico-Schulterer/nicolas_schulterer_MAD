package com.example.learningdiary2
import com.example.learningdiary2.models.Movie

interface InterfaceMovieRepo {
    fun getMovies() : List<Movie>
    fun addMovie(movie: Movie)
}