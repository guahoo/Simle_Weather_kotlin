package com.simple_weather.data.repository

import androidx.lifecycle.LiveData
import com.simple_weather.data.db.entity.weather_entry.ConditionCurrentWeatherEntry
import com.simple_weather.data.db.entity.weather_entry.ConditionFutureWeatherEntry
import com.simple_weather.data.db.entity.weather_entry.ConditionDailyWeather

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<out ConditionCurrentWeatherEntry>
    suspend fun getFutureWeather(): LiveData<out List<ConditionFutureWeatherEntry>>
    suspend fun getFutureWeatherByDt(dt:Long): LiveData<out ConditionFutureWeatherEntry>
    suspend fun getDailyWeather(loc:String, date:Long): LiveData<out List<ConditionDailyWeather>>
}