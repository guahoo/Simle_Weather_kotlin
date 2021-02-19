package com.simple_weather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.simple_weather.data.db.entity.weather_entry.CurrentWeatherEntry
import com.simple_weather.internal.LocalDateConverter
import com.simple_weather.data.db.entity.weather_entry.FutureHourlyWeatherEntry
import com.simple_weather.data.db.entity.weather_entry.FutureDailyWeatherEntry


@Database(
    entities = [CurrentWeatherEntry::class, FutureHourlyWeatherEntry::class, FutureDailyWeatherEntry::class],
    version = 1
)

@TypeConverters(LocalDateConverter::class)
abstract class ForecastDataBase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun futureWeatherDao(): FutureWeatherDao
    abstract fun dailyWeatherDao(): FutureDailyDao

    companion object{
        @Volatile private var instance: ForecastDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }

        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, ForecastDataBase::class.java, "forecast.db").build()



    }

}