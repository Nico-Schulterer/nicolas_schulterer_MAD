package com.example.learningdiary.repositories

import com.example.learningdiary.data.MovieDao
import com.example.learningdiary.models.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    suspend fun add(movie: Movie) = movieDao.add(movie)

    suspend fun delete(movie: Movie) = movieDao.delete(movie)

    suspend fun update(movie: Movie) = movieDao.update(movie)

    fun getAllMovies() : Flow<List<Movie>> = movieDao.getAll()

    fun getAllFavorites() : Flow<List<Movie>> = movieDao.getAllFavorites()

    suspend fun getMovieByID(id: String) : Movie = movieDao.getMovie(id)

}