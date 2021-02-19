package com.simple_weather.data.db.entity.future.futureDaily

import androidx.room.ColumnInfo

data class ConditionDailyWeather(

//        id,clouds,dewPoint,dt,feelsLike,humidity,pop,pressure,temp,uvi,visibility,weather,windDeg,windSpeed
@ColumnInfo(name = "dt")
val dt: Long,
@ColumnInfo(name = "windDeg") val wind_deg: Int,

@ColumnInfo(name = "windSpeed") val wind_speed: Double,

@ColumnInfo(name = "cityName") val cityName: String,

// @ColumnInfo(name = "sys_sunset") val sys_sunset: Long,

@ColumnInfo(name = "temp_day") val day_temp: Double,

@ColumnInfo(name = "temp_night") val night_temp: Double,

@ColumnInfo(name = "feelsLike_day") val day: Double,

@ColumnInfo(name = "humidity") val main_humidity: Double,

@ColumnInfo(name = "rain") val rain: Double,
@ColumnInfo(name = "snow") val snow: Double,
@ColumnInfo(name = "sunrise") val sunrise: Int,
@ColumnInfo(name = "sunset") val sunset: Int,

@ColumnInfo(name = "pressure") val main_pressure: Double,

@ColumnInfo(name = "weather") val weather: String,

//@ColumnInfo(name = "visibility") val visibility: Int
)

