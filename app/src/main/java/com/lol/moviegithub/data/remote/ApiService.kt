package com.lol.movies.data.remote

import com.lol.movies.data.model.MovieDetails
import com.lol.movies.data.model.MovieResponse
import com.lol.movies.data.model.MovieTrailer
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
  @GET("movie/now_playing")
  suspend fun getNowPlayingMovies(
      @Query("api_key") apiKey: String = API_KEY,
      @Query("page") page: Int = 1,
      @Query("include_adult") adult: Boolean = false,
  ): MovieResponse

  @GET("movie/popular")
  suspend fun getPopularMovies(
      @Query("api_key") apiKey: String = API_KEY,
      @Query("page") page: Int = 1,
      @Query("include_adult") adult: Boolean = false,
  ): MovieResponse

  @GET("movie/upcoming")
  suspend fun getUpComingMovies(
      @Query("api_key") apiKey: String = API_KEY,
      @Query("page") page: Int = 1,
      @Query("include_adult") adult: Boolean = false,
  ): MovieResponse

  @GET("movie/top_rated")
  suspend fun getTopRatedMovies(
      @Query("api_key") apiKey: String = API_KEY,
      @Query("page") page: Int = 1,
      @Query("include_adult") adult: Boolean = false,
  ): MovieResponse



  @GET("search/movie")
  suspend fun searchMovies(
      @Query("api_key") apiKey: String = API_KEY,
      @Query("query") query: String,
      @Query("include_adult") adult: Boolean = false,
      @Query("page") page: Int = 1,
  ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieDetails
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailer(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieTrailer

  companion object {
    private const val API_KEY = "707c7ad385782eadd54247e2d31da51d"
  }
}
