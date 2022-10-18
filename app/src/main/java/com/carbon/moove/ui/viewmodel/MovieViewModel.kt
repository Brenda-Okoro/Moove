package com.carbon.moove.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carbon.core.models.data.Movies
import com.carbon.moove.repository.MovieRepository
import com.carbon.moove.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _movies = MediatorLiveData<Resource<List<Movies>>>()
    val movies: LiveData<Resource<List<Movies>>>
        get() = _movies

    private val _movieDetail = MediatorLiveData<Resource<Movies>>()
    val movieDetail: LiveData<Resource<Movies>>
        get() = _movieDetail


    init {
        pullMovies()
        movieDetail.value?.data?.let { getMovie(it.id) }
    }

    fun pullMovies() {
        viewModelScope.launch {
            _movies.addSource(movieRepository.getPopularTvShows()) {
                _movies.postValue(it)
            }
        }
    }

    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            _movieDetail.addSource(movieRepository.getDetails(movieId)) {
                _movieDetail.postValue(it)
            }
        }
    }
}
