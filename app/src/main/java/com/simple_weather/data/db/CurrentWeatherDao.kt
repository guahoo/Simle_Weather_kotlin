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
//"select * from current_weather where id = $CURRENT_WEATHER_ID"
    @Query("select * from current_weather where idCurrentWeather = $CURRENT_WEATHER_ID")
    fun getCurrentWeather() : LiveData<ConditionCurrentWeatherEntry>

    @Query("select * from current_weather where idCurrentWeather = $CURRENT_WEATHER_ID")
    fun getCurrentWeatherNonLive(): CurrentWeatherEntry?

//    @Query("select name from current_weather where idCurrentWeather = $CURRENT_WEATHER_ID")
//    fun getCityNAme (id: String): String?

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun update(id: String): String
}