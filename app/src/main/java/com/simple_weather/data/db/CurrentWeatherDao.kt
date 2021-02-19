package com.simple_weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.simple_weather.data.db.entity.weather_entry.CURRENT_WEATHER_ID
import com.simple_weather.data.db.entity.weather_entry.ConditionCurrentWeatherEntry
import com.simple_weather.data.db.entity.weather_entry.CurrentWeatherEntry


@Dao
interface CurrentWeatherDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from current_weather where idCurrentWeather = $CURRENT_WEATHER_ID")
    fun getCurrentWeather() : LiveData<ConditionCurrentWeatherEntry>

//    @Query("select name from current_weather where idCurrentWeather = $CURRENT_WEATHER_ID")
//    fun getCityNAme (id: String): String?

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun update(id: String): String
}