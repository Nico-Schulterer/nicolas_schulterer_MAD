package com.example.learningdiary.utils

import android.content.Context
import com.example.learningdiary.data.MovieDatabase
import com.example.learningdiary.repositories.MovieRepository
import com.example.learningdiary.viewModels.AddMovieScreenViewModelFactory
import com.example.learningdiary.viewModels.DetailScreenViewModelFactory
import com.example.learningdiary.viewModels.FavoriteScreenViewModelFactory
import com.example.learningdiary.viewModels.HomeScreenViewModelFactory

object InjectorUtils {
    private fun getMovieRepository(context: Context) : MovieRepository {
        return MovieRepository(MovieDatabase.getDatabase(context).movieDao())
    }

    fun provideHomeScreenViewModelFactory(context: Context): HomeScreenViewModelFactory {
        val repository = getMovieRepository(context)
        return HomeScreenViewModelFactory(repository)
    }

    fun provideFavoriteScreenViewModelFactory(context: Context): FavoriteScreenViewModelFactory {
        val repository = getMovieRepository(context)
        return FavoriteScreenViewModelFactory(repository)
    }

    fun provideDetailScreenViewModelFactory(context: Context): DetailScreenViewModelFactory {
        val repository = getMovieRepository(context)
        return DetailScreenViewModelFactory(repository)
    }

    fun provideAddMovieScreenViewModelFactory(context: Context): AddMovieScreenViewModelFactory {
        val repository = getMovieRepository(context)
        return AddMovieScreenViewModelFactory(repository)
    }
}