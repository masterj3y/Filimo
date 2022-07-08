package com.sabaidea.search.ui

import com.sabaidea.search.model.Movie

data class SearchMovieState(
    val loading: Boolean,
    val result: List<Movie>,
    val errorOccurred: Boolean
) {

    companion object {

        fun initial() = SearchMovieState(
            loading = false,
            result = emptyList(),
            errorOccurred = false
        )
    }
}