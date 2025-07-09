package com.rahul.airfiindia.domain.usecase

import com.rahul.airfiindia.data.model.Airline
import com.rahul.airfiindia.data.repository.AirlineRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: AirlineRepository
) {
    suspend operator fun invoke(airlineId: Airline) = repository.toggleFavorite(airlineId)
}