package com.sabaidea.search.ui

import app.cash.turbine.test
import com.sabaidea.search.data.repository.MovieRepositoryImpl
import com.sabaidea.search.data.source.MovieRemoteDataSource
import com.sabaidea.search.model.Movie
import com.sabaidea.search.model.MoviePicture
import com.sabaidea.search.model.SearchMovieResponse
import com.sabaidea.search.model.State
import com.sabaidea.testutils.coroutines.CoroutinesTestRule
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SearchMovieViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var remoteDataSource: MovieRemoteDataSource
    private lateinit var viewModel: SearchMovieViewModel

    @Before
    fun setup() {
        remoteDataSource = mock()
        val repository = MovieRepositoryImpl(remoteDataSource)
        viewModel = SearchMovieViewModel(
            repository = repository,
            coroutineDispatcher = StandardTestDispatcher()
        )
    }

    @Test
    fun `test search movie`() = runTest {

        val query = "iran"

        whenever(remoteDataSource.searchMovie(query)).thenReturn(
            flow {
                emit(State.onLoading())
                emit(State.onSuccess(mockedSearchResult))
            }
        )

        with(viewModel) {

            state.test {
                awaitItem().run {
                    this.loading shouldBe false
                    this.result.size shouldBe 0
                    this.errorOccurred shouldBe false
                }
                search(query)
                awaitItem().run {
                    this.loading shouldBe true
                    this.result.size shouldBe 0
                    this.errorOccurred shouldBe false
                }
                awaitItem().run {
                    this.loading shouldBe false
                    this.result.size shouldBe 2
                    this.errorOccurred shouldBe false
                }
                expectNoEvents()
            }
        }
    }

    @Test
    fun `test search movie causes an error`() = runTest {

        val query = "iran"

        whenever(remoteDataSource.searchMovie(query)).thenReturn(
            flow {
                emit(State.onLoading())
                emit(State.onError())
            }
        )

        with(viewModel) {

            state.test {
                awaitItem().run {
                    this.loading shouldBe false
                    this.result.size shouldBe 0
                    this.errorOccurred shouldBe false
                }
                search(query)
                awaitItem().run {
                    this.loading shouldBe true
                    this.result.size shouldBe 0
                    this.errorOccurred shouldBe false
                }
                awaitItem().run {
                    this.loading shouldBe false
                    this.result.size shouldBe 0
                    this.errorOccurred shouldBe true
                }
                expectNoEvents()
            }
        }
    }

    @Test
    fun `test search movie throws an exception`() = runTest {

        val query = "iran"

        whenever(remoteDataSource.searchMovie(query)).thenReturn(
            flow {
                emit(State.onLoading())
                emit(State.onException())
            }
        )

        with(viewModel) {

            state.test {
                awaitItem().run {
                    this.loading shouldBe false
                    this.result.size shouldBe 0
                    this.errorOccurred shouldBe false
                }
                search(query)
                awaitItem().run {
                    this.loading shouldBe true
                    this.result.size shouldBe 0
                    this.errorOccurred shouldBe false
                }
                awaitItem().run {
                    this.loading shouldBe false
                    this.result.size shouldBe 0
                    this.errorOccurred shouldBe true
                }
                expectNoEvents()
            }
        }
    }

    companion object {

        private val mockedSearchResult = SearchMovieResponse(
            result = listOf(
                Movie(
                    title = "ایران",
                    englishTitle = "Iran",
                    picture = MoviePicture(
                        small = "small",
                        medium = "medium",
                        big = "big"
                    )
                ),
                Movie(
                    title = "باشگاه مشت‌زنی",
                    englishTitle = "fight club",
                    picture = MoviePicture(
                        small = "small",
                        medium = "medium",
                        big = "big"
                    )
                )
            )
        )
    }
}