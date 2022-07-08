package com.sabaidea.search.model

import com.squareup.moshi.Json

data class Movie(
    @field:Json(name = "movie_title")
    val title: String,
    @field:Json(name = "movie_title_en")
    val englishTitle: String,
    @field:Json(name = "pic")
    val picture: MoviePicture
)