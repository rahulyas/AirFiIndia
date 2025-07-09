package com.rahul.airfiindia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.airfiindia.data.model.Airline
import com.rahul.airfiindia.domain.usecase.GetAirlineDetailUseCase
import com.rahul.airfiindia.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirlineDetailViewModel @Inject constructor(
    private val getAirlineDetailUseCase: GetAirlineDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    ): ViewModel() {

    private val _airline = MutableStateFlow<Airline?>(null)
    val airline = _airline.asStateFlow()

    fun loadAirline(airlineId: String) {
        viewModelScope.launch {
            _airline.value = getAirlineDetailUseCase.invoke(airlineId)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _airline.value?.let { airline ->
                toggleFavoriteUseCase.invoke(airline)
                _airline.value = airline.copy(isFavorite = !airline.isFavorite)
            }
        }
    }
}