package com.simple_weather.data.db.entity.current

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.simple_weather.data.db.converters.ListTypeConvertersCurrentWeather


const val CURRENT_WEATHER_ID = 0



@TypeConverters(ListTypeConvertersCurrentWeather::class)
@Entity(tableName = "current_weather")
data class CurrentWeatherEntry (
        @SerializedName("base")
    val base: String,
        @SerializedName("clouds")
    @Embedded(prefix = "clouds_")
    val clouds: Clouds,
        @SerializedName("cod")
    val cod: Int,
        @Embedded(prefix = "coord_")
    @SerializedName("coord")
    val coord: Coord,
        @SerializedName("dt")
    val dt: Long,
        @SerializedName("id")
    val id: Int,
        @Embedded(prefix = "main_")
    @SerializedName("main")
    val main: Main,
        @SerializedName("name")
    var name: String,
        @Embedded(prefix = "sys_")
    @SerializedName("sys")
    val sys: Sys,
        @SerializedName("timezone")
    val timezone: Int,
        @SerializedName("visibility")
    val visibility: Int,
        @SerializedName("weather")
    val weather: List<Weather>,
        @Embedded(prefix = "wind_")
    @SerializedName("wind")
    val wind: Wind)
    {
        @PrimaryKey(autoGenerate = false)
        var idCurrentWeather: Int = CURRENT_WEATHER_ID

    }


