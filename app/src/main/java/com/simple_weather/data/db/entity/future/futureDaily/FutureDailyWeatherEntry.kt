package com.simple_weather.data.db.entity.future.futureDaily


import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.simple_weather.data.db.converters.ListTypeConvertersFutureWeather
import com.simple_weather.data.db.entity.future.WeatherX

@Entity(tableName = "daily_weather", indices = [Index(value = ["dt"], unique = true)])
@TypeConverters(ListTypeConvertersFutureWeather::class)
data class FutureDailyWeatherEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val clouds: Int,
    var cityName: String,
    @SerializedName("dew_point")
    val dewPoint: Double,
    val dt: Long,
    @Embedded(prefix = "feelsLike_")
    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val snow: Double,
    val sunrise: Int,
    val sunset: Int,
    @Embedded(prefix = "temp_")
    val temp: Temp,
    val uvi: Double,
    val weather: List<WeatherX>,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double
)