package com.sabaidea.search.model

import com.squareup.moshi.Json

data class SearchMovieResponse(
    @field:Json(name = "data")
    val result: List<Movie>
)
