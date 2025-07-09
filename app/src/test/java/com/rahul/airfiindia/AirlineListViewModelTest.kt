package com.rahul.airfiindia

import app.cash.turbine.test
import com.rahul.airfiindia.data.model.Airline
import com.rahul.airfiindia.domain.usecase.GetAirlinesUseCase
import com.rahul.airfiindia.domain.usecase.LoadAirlinesFromJsonUseCase
import com.rahul.airfiindia.domain.usecase.SearchAirlinesUseCase
import com.rahul.airfiindia.domain.usecase.ToggleFavoriteUseCase
import com.rahul.airfiindia.ui.viewmodel.AirlineListViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AirlineListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: AirlineListViewModel

    private val getAirlinesUseCase: GetAirlinesUseCase = mockk()
    private val loadAirlinesFromJsonUseCase: LoadAirlinesFromJsonUseCase = mockk()
    private val searchAirlinesUseCase: SearchAirlinesUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk()

    private val dummyAirlines = listOf(
        Airline("1", "EasyJet", "UK", "London", 342, "https://...", "https://..."),
        Airline("2", "IndiGo", "India", "Gurgaon", 200, "https://...", "https://...")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { loadAirlinesFromJsonUseCase.invoke() } just Runs
        coEvery { getAirlinesUseCase.invoke() } returns flowOf(dummyAirlines)
        coEvery { searchAirlinesUseCase.invoke(any()) } returns flowOf(dummyAirlines)

        viewModel = AirlineListViewModel(
            getAirlinesUseCase,
            loadAirlinesFromJsonUseCase,
            searchAirlinesUseCase,
            toggleFavoriteUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `airlines flow should emit full list when searchQuery is blank`() = runTest {
        viewModel.updateSearchQuery("")
        advanceTimeBy(350)

        viewModel.airlines.test {
            val result = awaitItem()
            assertEquals(dummyAirlines, result)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `airlines flow should call search when query is not blank`() = runTest {
        val searchResult = dummyAirlines.filter { it.name.contains("IndiGo", ignoreCase = true) }

        coEvery { searchAirlinesUseCase.invoke("IndiGo") } returns flowOf(searchResult)

        viewModel.updateSearchQuery("IndiGo")
        advanceTimeBy(350)

        viewModel.airlines.test {
            val result = awaitItem()
            assertEquals(searchResult, result)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleFavorite should invoke use case`() = runTest {
        val airline = dummyAirlines[0]
        coEvery { toggleFavoriteUseCase.invoke(airline) } just Runs

        viewModel.toggleFavorite(airline)
        advanceUntilIdle()

        coVerify { toggleFavoriteUseCase.invoke(airline) }
    }
}
