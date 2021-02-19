package com.simple_weather.data.db.entity.weather_entry

import androidx.room.ColumnInfo
import androidx.room.TypeConverters

import com.simple_weather.data.db.converters.ListTypeConvertersWeather

@TypeConverters(ListTypeConvertersWeather::class)
data class ConditionCurrentWeatherEntry(
        //idCurrentWeather,clouds,dewPoint,dt,feelsLike,humidity,pressure,sunrise,sunset,temp,uvi,visibility,weather,windDeg,windSpeed
        @ColumnInfo(name = "dt")
        val dt: Long,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "windDeg")
        val wind_deg: Int,

        @ColumnInfo(name = "windSpeed")
        val wind_speed: Double,

        @ColumnInfo(name = "sunrise")
        val sys_sunrise: Long,

        @ColumnInfo(name = "sunset")
        val sys_sunset: Long,

        @ColumnInfo(name = "temp")
        val main_temp: Double,

        @ColumnInfo(name = "feelsLike")
        val main_feelsLike: Double,

        @ColumnInfo(name = "humidity")
        val main_humidity: Double,

        @ColumnInfo(name = "pressure")
        val main_pressure: Double,

        @ColumnInfo(name = "weather")
        val weather: String,

        @ColumnInfo(name = "visibility")

        val visibility: Int
)








