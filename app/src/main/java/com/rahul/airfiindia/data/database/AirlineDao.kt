package com.rahul.airfiindia.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rahul.airfiindia.data.model.Airline
import kotlinx.coroutines.flow.Flow

@Dao
interface AirlineDao {
    @Query("SELECT * FROM airlines")
    fun getAllAirlines(): Flow<List<Airline>>

    @Query("SELECT * FROM airlines WHERE id = :id")
    suspend fun getAirlineById(id: String): Airline?

    @Query("SELECT * FROM airlines WHERE name LIKE '%' || :query || '%' OR country LIKE '%' || :query || '%'")
    fun searchAirlines(query: String): Flow<List<Airline>>

    @Query("SELECT * FROM airlines WHERE isFavorite = 1")
    fun getFavoriteAirlines(): Flow<List<Airline>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAirlines(airlines: List<Airline>)

    @Update
    suspend fun updateAirline(airline: Airline)

    @Query("DELETE FROM airlines")
    suspend fun deleteAllAirlines()
}
