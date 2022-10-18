package com.carbon.moove.api

import com.carbon.core.models.PaginatedListResponse
import com.carbon.core.models.api.MovieApiHelper
import com.carbon.core.models.api.MovieService
import com.carbon.core.models.data.Movies
import retrofit2.Response
import javax.inject.Inject

class MovieHelperImp @Inject constructor(private val apiService: MovieService) : MovieApiHelper {

    override suspend fun getPopularTvShows(
        query: String?,
        page: Int?
    ): Response<PaginatedListResponse<Movies>> = apiService.getPopularTvShows()

    override suspend fun getDetails(movieId: Int, query: String?): Response<Movies> =
        apiService.getDetails(movieId, query)

}