package com.carbon.moove.repository.local

import com.carbon.core.models.data.Movies
import com.carbon.moove.data.MoviesDao
import javax.inject.Inject

class MovieLocalRepository @Inject constructor(
    private val localDataSource: MoviesDao
) {

    fun fetchMovies(): List<Movies> =
        localDataSource.getAllMovies()

    suspend fun insertMovies(movies: List<Movies>) =
        localDataSource.insert(movies)

    fun getMovie(movieId: Int) =
        localDataSource.getMovie(movieId)

    suspend fun deleteMovies(movies: Movies) =
        localDataSource.delete(movies)

}