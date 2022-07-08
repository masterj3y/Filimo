package com.sabaidea.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabaidea.coroutines.ViewModelCoroutineDispatcher
import com.sabaidea.search.data.repository.MovieRepository
import com.sabaidea.search.model.SearchMovieResponse
import com.sabaidea.search.model.State
import com.sabaidea.search.model.StateStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    @ViewModelCoroutineDispatcher
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(SearchMovieState.initial())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    fun search(query: String) {

        if (query.isBlank()) return

        searchJob?.cancel()
        searchJob = viewModelScope.launch(coroutineDispatcher) {
            delay(700)
            repository.searchMovie(query)
                .onEach(::mapToState)
                .launchIn(this)
        }
    }

    private fun mapToState(state: State<SearchMovieResponse>) {
        when (state.status) {
            is StateStatus.Loading -> _state.update {
                println("loading")
                it.copy(
                    loading = true,
                    result = emptyList(),
                    errorOccurred = false
                )
            }
            is StateStatus.Success -> _state.update {
                it.copy(
                    loading = false,
                    result = state.data?.result ?: listOf(),
                    errorOccurred = false
                )
            }
            is StateStatus.Error -> _state.update {
                it.copy(
                    loading = false,
                    result = emptyList(),
                    errorOccurred = true
                )
            }
            is StateStatus.Exception -> _state.update {
                it.copy(
                    loading = false,
                    result = emptyList(),
                    errorOccurred = true
                )
            }
        }
    }
}