package com.simple_weather.data.db.entity.current

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.simple_weather.data.db.WeatherParameters

import com.simple_weather.data.db.converters.ListTypeConvertersCurrentWeather

@TypeConverters(ListTypeConvertersCurrentWeather::class)
data class ConditionCurrentWeatherEntry(

        @ColumnInfo(name = "dt")
        override val dt: Long,

        @ColumnInfo(name = "name")
        override val name: String,

        @ColumnInfo(name = "wind_deg")
        override val wind_deg: Int,

        @ColumnInfo(name = "wind_speed")
        override val wind_speed: Double,

        @ColumnInfo(name = "sys_sunrise")
        override val sys_sunrise: Long,

        @ColumnInfo(name = "sys_sunset")
        override val sys_sunset: Long,

        @ColumnInfo(name = "main_temp")
        override val main_temp: Double,

        @ColumnInfo(name = "main_feelsLike")
        override val main_feelsLike: Double,

        @ColumnInfo(name = "main_humidity")
        override val main_humidity: Double,

        @ColumnInfo(name = "main_pressure")
        override val main_pressure: Double,

        @ColumnInfo(name = "weather")
        override val weather: String,

        @ColumnInfo(name = "visibility")

        override val visibility: Int
): WeatherParameters








