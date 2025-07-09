package com.rahul.airfiindia.domain.usecase

import com.rahul.airfiindia.data.repository.AirlineRepository
import javax.inject.Inject

class GetFavoriteAirlinesUseCase @Inject constructor(private val repository: AirlineRepository) {
    suspend operator fun invoke() = repository.getFavoriteAirlines()
}