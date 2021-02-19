package com.simple_weather.data.repository

import androidx.lifecycle.LiveData
import com.simple_weather.data.db.entity.current.ConditionCurrentWeatherEntry
import com.simple_weather.data.db.entity.future.forecastByHours.ConditionFutureWeatherEntry
import com.simple_weather.data.db.entity.future.futureDaily.ConditionDailyWeather

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<out ConditionCurrentWeatherEntry>
    suspend fun getFutureWeather(): LiveData<out List<ConditionFutureWeatherEntry>>
    suspend fun getFutureWeatherByDt(dt:Long): LiveData<out ConditionFutureWeatherEntry>
    suspend fun getDailyWeather(): LiveData<out List<ConditionDailyWeather>>
}