package com.rahul.airfiindia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.airfiindia.data.model.Airline
import com.rahul.airfiindia.domain.usecase.GetAirlinesUseCase
import com.rahul.airfiindia.domain.usecase.LoadAirlinesFromJsonUseCase
import com.rahul.airfiindia.domain.usecase.SearchAirlinesUseCase
import com.rahul.airfiindia.domain.usecase.ToggleFavoriteUseCase
import com.rahul.airfiindia.ui.state.AirlineUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirlineListViewModel @Inject constructor(
    private val getAirlinesUseCase: GetAirlinesUseCase,
    private val loadAirlinesFromJsonUseCase: LoadAirlinesFromJsonUseCase,
    private val searchAirlinesUseCase: SearchAirlinesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<AirlineUiState> = combine(
        _searchQuery,
        _isLoading
    ) { query, isLoading ->
        AirlineUiState(
            searchQuery = query,
            isLoading = isLoading,
            isSearching = query.isNotBlank()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AirlineUiState()
    )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val airlines: StateFlow<List<Airline>> = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                getAirlinesUseCase.invoke()
            } else {
                searchAirlinesUseCase.invoke(query)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        loadAirlines()
    }

    private fun loadAirlines() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                loadAirlinesFromJsonUseCase.invoke()
            } catch (e: Exception) {
                println("Error loading airlines: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleFavorite(airline: Airline) {
        viewModelScope.launch {
            toggleFavoriteUseCase.invoke(airline)
        }
    }
}