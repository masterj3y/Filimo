package com.sabaidea.search.data.repository

import com.sabaidea.search.data.source.MovieRemoteDataSource
import com.sabaidea.search.model.SearchMovieResponse
import com.sabaidea.search.model.State
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun searchMovie(query: String): Flow<State<SearchMovieResponse>>
}

class MovieRepositoryImpl(private val remoteDataSource: MovieRemoteDataSource) : MovieRepository {

    override fun searchMovie(query: String): Flow<State<SearchMovieResponse>> =
        remoteDataSource.searchMovie(query)
}