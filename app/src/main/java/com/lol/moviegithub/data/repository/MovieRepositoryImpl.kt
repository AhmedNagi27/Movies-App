package com.lol.movies.data.repository

import com.lol.movies.data.model.MovieDetails
import com.lol.movies.data.model.MovieResponse
import com.lol.movies.data.model.MovieTrailer
import com.lol.movies.data.remote.RetrofitInstance
import com.lol.movies.util.UiState


class MovieRepositoryImpl : MovieRepository {


    override suspend fun getNowPlayingMovies(page: Int): UiState<MovieResponse> {
        return try {
            val response = RetrofitInstance.api.getNowPlayingMovies(page = page)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getPopularMovies(page: Int): UiState<MovieResponse> {
         return try {
            val response = RetrofitInstance.api.getPopularMovies(page = page)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getTopRatedMovies(page: Int): UiState<MovieResponse> {
         return try {
            val response = RetrofitInstance.api.getTopRatedMovies(page = page)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getUpcomingMovies(page: Int): UiState<MovieResponse> {
         return try {
            val response = RetrofitInstance.api.getUpComingMovies(page = page)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun searchMovies(query: String, page: Int): UiState<MovieResponse> {
        return try {
            val response = RetrofitInstance.api.searchMovies(query = query, page = page)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getMovieDetails(movieId: Int): UiState<MovieDetails> {
        return try {
            val response = RetrofitInstance.api.getMovieDetails(movieId = movieId)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getMovieTrailer(movieId: Int): UiState<MovieTrailer> {
        return try {
            val response = RetrofitInstance.api.getMovieTrailer(movieId = movieId)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error")
        }
    }

}


