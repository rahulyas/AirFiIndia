package com.rahul.airfiindia.data.source

import android.content.Context
import com.rahul.airfiindia.data.model.Airline
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    suspend fun getAirlines(): List<Airline> = withContext(Dispatchers.IO) {
        val json = context.assets.open("airlines.json").bufferedReader().use { it.readText() }
        Json.decodeFromString(json)
    }
}