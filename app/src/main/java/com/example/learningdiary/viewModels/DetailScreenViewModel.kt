package com.example.learningdiary.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningdiary.models.Movie
import com.example.learningdiary.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movieList = MutableStateFlow(listOf<Movie>())
    val movies: StateFlow<List<Movie>> =_movieList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllMovies().collect { movieList ->
                if (!movieList.isNullOrEmpty())
                    _movieList.value = movieList
            }
        }
    }

    suspend fun changeFavorite(movieID: String) {
        val movie: Movie = repository.getMovieByID(movieID)
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }

    fun getMovieByID(movieID: String): Movie {
        return movies.value.first {
            it.id == movieID
        }
    }
}