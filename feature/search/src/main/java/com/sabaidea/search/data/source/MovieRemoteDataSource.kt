package com.sabaidea.search.data.source

import com.sabaidea.search.data.service.SearchService
import com.sabaidea.search.extension.networkRequest
import com.sabaidea.search.model.SearchMovieResponse
import com.sabaidea.search.model.State
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {

    fun searchMovie(query: String): Flow<State<SearchMovieResponse>>
}

class MovieRemoteDataSourceImpl(private val searchService: SearchService) : MovieRemoteDataSource {

    override fun searchMovie(query: String): Flow<State<SearchMovieResponse>> = networkRequest {
        searchService.search(query = query)
    }
}