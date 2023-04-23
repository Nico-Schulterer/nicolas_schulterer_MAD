package com.example.learningdiary.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.learningdiary.InterfaceMovieRepo

@Entity
data class Movie(
    @PrimaryKey val id: String,
    val title: String,
    val year: String,
    val genre: List<Genre>,
    val director: String,
    val actors: String,
    val plot: String,
    val images: List<String>,
    val rating: Float,
    var isFavorite: Boolean = false
)
