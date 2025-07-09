package com.rahul.airfiindia

import com.rahul.airfiindia.data.model.Airline
import com.rahul.airfiindia.domain.usecase.GetAirlineDetailUseCase
import com.rahul.airfiindia.domain.usecase.ToggleFavoriteUseCase
import com.rahul.airfiindia.ui.viewmodel.AirlineDetailViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class AirlineDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: AirlineDetailViewModel

    private val getAirlineDetailUseCase: GetAirlineDetailUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AirlineDetailViewModel(getAirlineDetailUseCase, toggleFavoriteUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadAirline should update airline state`() = runTest {
        val dummyAirline = Airline("1", "EasyJet", "UK", "London", 342, "https://...",  "https://...",)

        coEvery { getAirlineDetailUseCase("1") } returns dummyAirline

        viewModel.loadAirline("1")
        advanceUntilIdle()

        assertEquals(dummyAirline, viewModel.airline.value)
    }

    @Test
    fun `toggleFavorite should update isFavorite value`() = runTest {
        val airline = Airline("1", "EasyJet", "UK", "London", 342, "https://...", "https://...",)

        viewModel = AirlineDetailViewModel(getAirlineDetailUseCase, toggleFavoriteUseCase)
        viewModel.loadAirline("1")

        coEvery { getAirlineDetailUseCase("1") } returns airline
        coEvery { toggleFavoriteUseCase(any()) } just Runs

        viewModel.loadAirline("1")
        advanceUntilIdle()

        viewModel.toggleFavorite()
        advanceUntilIdle()

        assertEquals(true, viewModel.airline.value?.isFavorite)
    }
}
