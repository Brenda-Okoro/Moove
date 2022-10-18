package com.carbon.moove.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.carbon.core.models.PaginatedListResponse
import com.carbon.core.models.data.Movies
import com.carbon.moove.repository.local.MovieLocalRepository
import com.carbon.moove.repository.remote.MovieRemoteRepository
import com.carbon.moove.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val localRepository: MovieLocalRepository,
    private val remoteRepository: MovieRemoteRepository
) {
    suspend fun getPopularTvShows(): LiveData<Resource<List<Movies>>> = liveData {
        withContext(Dispatchers.IO) {
            val cache = localRepository.fetchMovies()
            emit(Resource.loading(cache))
            try {
                val response: Response<PaginatedListResponse<Movies>> =
                    remoteRepository.getPopularTvShows()
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    localRepository.insertMovies(movies ?: emptyList())
                    emit(Resource.success(movies))
                } else {
                    emit(Resource.error(response.errorBody().toString(), cache))
                }
            } catch (ex: Exception) {
                emit(Resource.error("Something went wrong", cache))
            }

        }

    }

    suspend fun getDetails(movieId: Int): LiveData<Resource<Movies>> = liveData {
        withContext(Dispatchers.IO) {
            val cache: Movies? = localRepository.getMovie(movieId)
            emit(Resource.loading(cache))
            try {
                val response: Response<Movies> =
                    remoteRepository.getDetails(movieId)
                if (response.isSuccessful) {
                    val movies: Movies? = response.body()
                    localRepository.insertMovies(listOf(movies).requireNoNulls())
                    emit(Resource.success(movies))
                } else {
                    emit(Resource.error(response.errorBody().toString(), cache))
                }
            } catch (ex: Exception) {
                emit(Resource.error("Something went wrong", cache))
            }
        }

    }
}