package com.example.learningdiary.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.learningdiary.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    //CRUD
    @Insert
    suspend fun add(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("SELECT * from movie")
    fun getAll(): Flow<List<Movie>>

    @Query("Select * from movie where isFavorite = 1")
    fun getAllFavorites() : Flow<List<Movie>>

    @Query("Select * from movie where id like :pk")
    suspend fun getMovie(pk: String) : Movie
}