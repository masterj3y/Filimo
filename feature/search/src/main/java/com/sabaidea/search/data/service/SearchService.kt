package com.sabaidea.search.data.service

import com.sabaidea.search.model.SearchMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SearchService {

    @GET("movie/movie/list/tagid/1000300/text/{query}/sug/on")
    suspend fun search(
        @Header("jsonType") jsonType: String = "simple",
        @Path("query") query: String
    ): Response<SearchMovieResponse>
}