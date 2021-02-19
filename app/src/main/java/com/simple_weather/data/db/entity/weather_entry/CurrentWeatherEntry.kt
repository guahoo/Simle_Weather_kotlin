package com.simple_weather.data.db.entity.weather_entry

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.simple_weather.data.db.converters.ListTypeConvertersWeather


const val CURRENT_WEATHER_ID = 0



@TypeConverters(ListTypeConvertersWeather::class)
@Entity(tableName = "current_weather")
data class CurrentWeatherEntry (
        var name: String,
        val clouds: Int,
        @SerializedName("dew_point")
        val dewPoint: Double,
        val dt: Int,
        @SerializedName("feels_like")
        val feelsLike: Double,
        val humidity: Int,
        val pressure: Int,
        val sunrise: Int,
        val sunset: Int,
        val temp: Double,
        val uvi: Double,
        val visibility: Int,
        val weather: List<Weather>,
        @SerializedName("wind_deg")
        val windDeg: Int,
        @SerializedName("wind_speed")
        val windSpeed: Double)
    {
        @PrimaryKey(autoGenerate = false)
        var idCurrentWeather: Int = CURRENT_WEATHER_ID

    }


