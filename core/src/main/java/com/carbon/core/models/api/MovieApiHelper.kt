package com.carbon.core.models.api

import com.carbon.core.models.PaginatedListResponse
import com.carbon.core.models.data.Movies
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiHelper {

    suspend fun getPopularTvShows(
        @Query("language") query: String? = null,
        @Query("page") page: Int? = null
    ): Response<PaginatedListResponse<Movies>>

    suspend fun getDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") query: String? = null
    ): Response<Movies>

}