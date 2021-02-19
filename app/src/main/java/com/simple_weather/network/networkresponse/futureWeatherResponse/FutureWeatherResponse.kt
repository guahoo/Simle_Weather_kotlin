package com.simple_weather.network.networkresponse.futureWeatherResponse


import com.google.gson.annotations.SerializedName
import com.simple_weather.data.db.entity.future.forecastByHours.FutureHourlyWeatherEntry

data class FutureWeatherResponse(
        val hourly: List<FutureHourlyWeatherEntry>,
        val lat: Double,
        val lon: Double,
        val timezone: String,
        @SerializedName("timezone_offset")
    val timezoneOffset: Int
)