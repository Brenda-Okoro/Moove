package com.carbon.moove.data

import androidx.room.*
import com.carbon.core.models.data.Movies

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<Movies>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovie(id: Int): Movies?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: List<Movies>)

    @Delete
    suspend fun delete(movie: Movies)

}