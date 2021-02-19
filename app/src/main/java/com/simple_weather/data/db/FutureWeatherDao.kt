package com.simple_weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.simple_weather.data.db.entity.weather_entry.ConditionFutureWeatherEntry
import com.simple_weather.data.db.entity.weather_entry.FutureHourlyWeatherEntry


@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureHourlyWeatherEntries: List<FutureHourlyWeatherEntry>)

    @Query("select * from future_weather")
    fun getFutureWeather() : LiveData <List<ConditionFutureWeatherEntry>>

    @Query("select * from future_weather where dt = :date")
    fun getDetailedFutureWeather(date:Long) : LiveData <ConditionFutureWeatherEntry>


}