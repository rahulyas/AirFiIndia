package com.rahul.airfiindia.domain.usecase

import com.rahul.airfiindia.data.repository.AirlineRepository
import javax.inject.Inject

class GetAirlinesUseCase @Inject constructor(
    private val repository: AirlineRepository
) {
    suspend operator fun invoke() = repository.getAllAirlines()
}