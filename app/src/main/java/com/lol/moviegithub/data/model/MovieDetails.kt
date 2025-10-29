package com.lol.movies.data.model

import com.squareup.moshi.Json

data class MovieDetails(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "poster_path") val poster_path: String?,
    @Json(name = "vote_average") val vote_average: Double?,
    @Json(name = "release_date") val release_date: String?
)
