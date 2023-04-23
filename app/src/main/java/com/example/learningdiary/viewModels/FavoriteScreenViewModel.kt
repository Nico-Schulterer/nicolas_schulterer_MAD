package com.example.learningdiary.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningdiary.models.Movie
import com.example.learningdiary.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movieListFavorites = MutableStateFlow(listOf<Movie>())
    val favMovieList: StateFlow<List<Movie>> = _movieListFavorites.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllFavorites().collect { movieList ->
                _movieListFavorites.value = movieList
            }
        }
    }

    suspend fun changeFavorite(movieID: String) {
        val movie: Movie = repository.getMovieByID(movieID)
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }
}