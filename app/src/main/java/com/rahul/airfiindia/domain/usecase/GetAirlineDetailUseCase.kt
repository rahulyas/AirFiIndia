package com.rahul.airfiindia.domain.usecase

import com.rahul.airfiindia.data.repository.AirlineRepository
import javax.inject.Inject

class GetAirlineDetailUseCase @Inject constructor(
    private val repository: AirlineRepository
) {
    suspend operator fun invoke(id: String) = repository.getAirlineById(id)
}