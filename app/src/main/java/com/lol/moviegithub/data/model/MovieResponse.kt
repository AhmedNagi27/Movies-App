package com.lol.movies.data.model

data class MovieResponse(
    val total_pages: Int,
    val results: List<Movie>,
)