package com.simple_weather.data.db.entity.future.forecastByHours


import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.simple_weather.data.db.converters.ListTypeConvertersFutureWeather
import com.simple_weather.data.db.entity.future.WeatherX


@Entity(tableName = "future_weather", indices = [Index(value = ["dt"], unique = true)])
@TypeConverters(ListTypeConvertersFutureWeather::class)
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
//        @Embedded(prefix = "snow_")
//        val snow: Snow,
        val temp: Double,
        val uvi: Double,
        val visibility: Int,
        val weather: List<WeatherX>,
        @SerializedName("wind_deg")
        val windDeg: Int,
        @SerializedName("wind_speed")
        val windSpeed: Double
)