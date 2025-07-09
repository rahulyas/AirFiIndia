package com.rahul.airfiindia.ui.state

import com.rahul.airfiindia.data.model.Airline

data class AirlineUiState(
    val airlines: List<Airline> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val isSearching: Boolean = false
)