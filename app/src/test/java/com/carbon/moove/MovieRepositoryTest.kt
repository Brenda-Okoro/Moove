package com.carbon.moove

import com.carbon.core.models.PaginatedListResponse
import com.carbon.core.models.data.Movies
import com.carbon.moove.repository.MovieRepository
import com.carbon.moove.repository.local.MovieLocalRepository
import com.carbon.moove.repository.remote.MovieRemoteRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {
    private lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var localRepository: MovieLocalRepository

    @Mock
    lateinit var remoteRepository: MovieRemoteRepository

    @Mock
    private lateinit var movie: Response<Movies>

    @Mock
    private lateinit var allMovies: Response<PaginatedListResponse<Movies>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        movieRepository = MovieRepository(localRepository, remoteRepository)
    }

    @Test
    fun fetchMoviesTest() {
        runBlocking {
            Mockito.`when`(remoteRepository.getPopularTvShows()).thenReturn(allMovies)
            val response = movieRepository.getPopularTvShows()
            assertEquals(
                allMovies.body()?.let {
                    PaginatedListResponse<Movies>(
                        it.page,
                        it.totalResults,
                        it.totalPages,
                        it.results
                    )
                }, response.value
            )
        }

    }

    @Test
    fun geMoviesTest() {
        runBlocking {
            Mockito.`when`(remoteRepository.getDetails(500)).thenReturn(movie)
            val response = movieRepository.getDetails(500)
            assertEquals(movie.body(), response.value)
        }

    }
}