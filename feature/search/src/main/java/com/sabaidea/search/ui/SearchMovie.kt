package com.sabaidea.search.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sabaidea.search.model.Movie

@Composable
fun SearchMovie(viewModel: SearchMovieViewModel) {

    val state by viewModel.state.collectAsState()

    val (query, setQuery) = rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = query,
            onValueChange = {
                setQuery(it)
                viewModel.search(it)
            },
            placeholder = {
                Text(text = "Movie name ...")
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        )

        Crossfade(targetState = state) { targetState ->
            when {
                targetState.loading -> Loading()
                !targetState.loading && targetState.result.isNotEmpty() -> MovieRows(movies = targetState.result)
                !targetState.loading && targetState.result.isEmpty() -> Empty()
                targetState.errorOccurred -> Error()
            }
        }
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun MovieRows(movies: List<Movie>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(movies) { movie ->
            MovieRow(movie = movie)
        }
    }
}

@Composable
private fun MovieRow(
    movie: Movie
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray.copy(alpha = .1f))
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = movie.picture.medium,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = movie.title, style = MaterialTheme.typography.h6)
            Text(text = movie.englishTitle)
        }
    }

}

@Composable
private fun Empty() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "No movies were found")
    }
}

@Composable
private fun Error() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "An error has occurred")
    }
}