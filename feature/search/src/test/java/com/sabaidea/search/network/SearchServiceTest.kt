package com.sabaidea.search.network

import com.sabaidea.search.data.service.SearchService
import com.sabaidea.search.network.base.SearviceTest
import com.sabaidea.testutils.coroutines.CoroutinesTestRule
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchServiceTest : SearviceTest<SearchService>() {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var searchService: SearchService

    @Before
    fun setup() {
        searchService = createService(SearchService::class.java)
    }

    @Test
    fun `test search movie`() = runTest {
        enqueueResponse("search/response.json")
        val response = searchService.search(query = "iran").body()

        response shouldNotBe null
        response?.result?.size shouldBe 40
    }
}