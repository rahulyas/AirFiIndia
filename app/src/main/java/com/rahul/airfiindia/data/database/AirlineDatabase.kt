package com.rahul.airfiindia.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rahul.airfiindia.data.model.Airline

@Database(
    entities = [Airline::class],
    version = 2,
    exportSchema = false
)
abstract class AirlineDatabase : RoomDatabase() {
    abstract fun airlineDao(): AirlineDao

    companion object {
        @Volatile
        private var INSTANCE: AirlineDatabase? = null

        fun getDatabase(context: Context): AirlineDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AirlineDatabase::class.java,
                    "airline_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
