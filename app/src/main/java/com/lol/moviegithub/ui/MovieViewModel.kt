package com.lol.moviegithub.ui

import androidx.lifecycle.*
import com.lol.movies.data.model.Movie
import com.lol.movies.data.model.MovieDetails
import com.lol.movies.data.model.MovieTrailer
import com.lol.movies.data.repository.MovieRepositoryImpl
import com.lol.movies.util.UiState
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
  private val repository = MovieRepositoryImpl()

  private val _uiState = MutableLiveData<UiState<List<Movie>>>()
  val uiState: LiveData<UiState<List<Movie>>> = _uiState

  private val _movieDetailsState = MutableLiveData<UiState<MovieDetails>>()
  val movieDetailsState: LiveData<UiState<MovieDetails>> = _movieDetailsState

  private val _movieTrailerState = MutableLiveData<UiState<MovieTrailer>>()
  val movieTrailerState: LiveData<UiState<MovieTrailer>> = _movieTrailerState

  private val _TotalPages = MutableLiveData<Int>()
  val TotalPages: LiveData<Int> = _TotalPages

  fun fetchNowPlaying(page: Int = 1) {
    viewModelScope.launch {
      _uiState.value = UiState.Loading
      val result = repository.getNowPlayingMovies(page = page)
      when (result) {
        is UiState.Success -> {
          _uiState.value = UiState.Success(result.data.results)
          _TotalPages.value = result.data.total_pages
        }
        is UiState.Error -> _uiState.value = UiState.Error(result.message)
        else -> {}
      }
    }
  }

  fun fetchPopular(page: Int = 1) {
    viewModelScope.launch {
      _uiState.value = UiState.Loading
      val result = repository.getPopularMovies(page = page)
      when (result) {
        is UiState.Success -> {
          _uiState.value = UiState.Success(result.data.results)
          _TotalPages.value = result.data.total_pages
        }
        is UiState.Error -> _uiState.value = UiState.Error(result.message)
        else -> {}
      }
    }
  }

  fun fetchUpComing(page: Int = 1) {
    viewModelScope.launch {
      _uiState.value = UiState.Loading
      val result = repository.getUpcomingMovies(page = page)
      when (result) {
        is UiState.Success -> {
          _uiState.value = UiState.Success(result.data.results)
          _TotalPages.value = result.data.total_pages
        }
        is UiState.Error -> _uiState.value = UiState.Error(result.message)
        else -> {}
      }
    }
  }

  fun fetchTopRated(page: Int = 1) {
    viewModelScope.launch {
      _uiState.value = UiState.Loading
      val result = repository.getTopRatedMovies(page = page)
      when (result) {
        is UiState.Success -> {
          _uiState.value = UiState.Success(result.data.results)
          _TotalPages.value = result.data.total_pages
        }
        is UiState.Error -> _uiState.value = UiState.Error(result.message)
        else -> {}
      }
    }
  }

  fun searchMovies(page: Int = 1, query: String) {
    viewModelScope.launch {
      _uiState.value = UiState.Loading
      val result = repository.searchMovies(query = query, page = page)
      when (result) {
        is UiState.Success -> {
          _uiState.value = UiState.Success(result.data.results)
          _TotalPages.value = result.data.total_pages
        }
        is UiState.Error -> _uiState.value = UiState.Error(result.message)
        else -> {}
      }
    }
  }

  fun fetchMovieDetails(movieId: Int) {
    viewModelScope.launch {
      _movieDetailsState.value = UiState.Loading
      val result = repository.getMovieDetails(movieId = movieId)
      when (result) {
        is UiState.Success -> {
          _movieDetailsState.value = result
        }
        is UiState.Error -> {
          _movieDetailsState.value = UiState.Error(result.message)
        }
        else -> {}
      }
    }
  }

  fun fetchMovieTrailer(movieId: Int) {
    viewModelScope.launch {
      _movieTrailerState.value = UiState.Loading
      val result = repository.getMovieTrailer(movieId = movieId)
      when (result) {
        is UiState.Success -> {
          _movieTrailerState.value = result
        }
        is UiState.Error -> {
          _movieTrailerState.value = UiState.Error(result.message)
        }
        else -> {}
      }
    }
  }
}
