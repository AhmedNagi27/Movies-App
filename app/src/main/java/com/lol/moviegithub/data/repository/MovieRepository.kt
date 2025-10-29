package com.lol.movies.data.repository

import com.lol.movies.data.model.MovieDetails
import com.lol.movies.data.model.MovieResponse
import com.lol.movies.data.model.MovieTrailer
import com.lol.movies.util.UiState

interface MovieRepository {
  suspend fun getNowPlayingMovies(page: Int = 1): UiState<MovieResponse>

  suspend fun getPopularMovies(page: Int = 1): UiState<MovieResponse>

  suspend fun getTopRatedMovies(page: Int = 1): UiState<MovieResponse>

  suspend fun getUpcomingMovies(page: Int = 1): UiState<MovieResponse>

  suspend fun searchMovies(query: String, page: Int = 1): UiState<MovieResponse>

  suspend fun getMovieDetails(movieId: Int): UiState<MovieDetails>

  suspend fun getMovieTrailer(movieId: Int): UiState<MovieTrailer>
}
