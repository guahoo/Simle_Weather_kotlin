package com.simple_weather.network.networkresponse


import com.google.gson.annotations.SerializedName
import com.simple_weather.data.db.entity.weather_entry.CurrentWeatherEntry
import com.simple_weather.data.db.entity.weather_entry.FutureHourlyWeatherEntry
import com.simple_weather.data.db.entity.weather_entry.Alert
import com.simple_weather.data.db.entity.weather_entry.FutureDailyWeatherEntry

data class WeatherAllInOneResponse(
        val alerts: List<Alert>,
        @SerializedName("current")
        val current: CurrentWeatherEntry,
        val daily: List<FutureDailyWeatherEntry>,
        val hourly: List<FutureHourlyWeatherEntry>,
        val lat: Double,
        val lon: Double,
        val timezone: String,
        @SerializedName("timezone_offset")
        val timezoneOffset: Int
)