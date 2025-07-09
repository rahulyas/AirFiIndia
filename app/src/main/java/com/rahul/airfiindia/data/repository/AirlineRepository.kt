package com.rahul.airfiindia.data.repository

import com.rahul.airfiindia.data.database.AirlineDao
import com.rahul.airfiindia.data.model.Airline
import com.rahul.airfiindia.data.source.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AirlineRepository @Inject constructor(
    private val airlineDao: AirlineDao,
    private val localDataSource: LocalDataSource
) {

    fun getAllAirlines(): Flow<List<Airline>> = airlineDao.getAllAirlines()

    suspend fun getAirlineById(id: String): Airline? = airlineDao.getAirlineById(id)

    fun searchAirlines(query: String): Flow<List<Airline>> = airlineDao.searchAirlines(query)

    fun getFavoriteAirlines(): Flow<List<Airline>> = airlineDao.getFavoriteAirlines()

    suspend fun toggleFavorite(airline: Airline) {
        val updatedAirline = airline.copy(isFavorite = !airline.isFavorite)
        airlineDao.updateAirline(updatedAirline)
    }

    suspend fun loadAirlinesFromJson() {
        val currentAirlines = airlineDao.getAllAirlines().first()
        if (currentAirlines.isEmpty()) {
            val airlines = localDataSource.getAirlines()
            println("Loading airlines from JSON: $airlines")
            airlineDao.insertAirlines(airlines)
        }
    }
}