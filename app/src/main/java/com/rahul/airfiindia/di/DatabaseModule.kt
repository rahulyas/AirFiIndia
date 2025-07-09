package com.rahul.airfiindia.di

import android.content.Context
import androidx.room.Room
import com.rahul.airfiindia.data.database.AirlineDao
import com.rahul.airfiindia.data.database.AirlineDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAirlineDatabase(@ApplicationContext context: Context): AirlineDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AirlineDatabase::class.java,
            "airline_database"
        )
        .fallbackToDestructiveMigration(false)
        .build()
    }

    @Provides
    fun provideAirlineDao(database: AirlineDatabase): AirlineDao {
        return database.airlineDao()
    }

}
