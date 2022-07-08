package com.sabaidea.search.model

import com.squareup.moshi.Json

data class MoviePicture(
    @field:Json(name = "movie_img_s")
    val small: String,
    @field:Json(name = "movie_img_m")
    val medium: String,
    @field:Json(name = "movie_img_b")
    val big: String,
)