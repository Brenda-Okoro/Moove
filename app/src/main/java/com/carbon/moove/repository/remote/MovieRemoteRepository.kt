package com.carbon.moove.repository.remote

import com.carbon.core.models.PaginatedListResponse
import com.carbon.core.models.api.MovieApiHelper
import com.carbon.core.models.data.Movies
import retrofit2.Response
import javax.inject.Inject

class MovieRemoteRepository @Inject constructor(private val apiHelper: MovieApiHelper) {

    suspend fun getPopularTvShows(): Response<PaginatedListResponse<Movies>> =
        apiHelper.getPopularTvShows()

    suspend fun getDetails(movieId: Int): Response<Movies> = apiHelper.getDetails(movieId)

}