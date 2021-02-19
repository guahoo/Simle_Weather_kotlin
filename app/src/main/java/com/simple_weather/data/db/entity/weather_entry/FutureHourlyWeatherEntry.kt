package com.simple_weather.data.db.entity.weather_entry

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.simple_weather.data.db.converters.ListTypeConvertersWeather


@Entity(tableName = "future_weather", indices = [Index(value = ["dt"], unique = true)])
@TypeConverters(ListTypeConvertersWeather::class)
data class FutureHourlyWeatherEntry(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        var cityName: String,
        val clouds: Int,
        @SerializedName("dew_point")
        val dewPoint: Double,
        val dt: Long,
        @SerializedName("feels_like")
        val feelsLike: Double,
        val humidity: Int,
        val pop: Double,
        val pressure: Int,
        val temp: Double,
        val uvi: Double,
        val visibility: Int,
        val weather: List<Weather>,
        @SerializedName("wind_deg")
        val windDeg: Int,
        @SerializedName("wind_speed")
        val windSpeed: Double
)