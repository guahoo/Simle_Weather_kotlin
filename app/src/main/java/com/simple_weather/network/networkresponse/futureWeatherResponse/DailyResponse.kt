package com.simple_weather.network.networkresponse.futureWeatherResponse


import com.google.gson.annotations.SerializedName
import com.simple_weather.data.db.entity.future.forecastByHours.FutureHourlyWeatherEntry
import com.simple_weather.data.db.entity.future.futureDaily.*

data class DailyResponse(
        val alerts: List<Alert>,
        val current: Current,
        val daily: List<FutureDailyWeatherEntry>,
        val hourly: List<FutureHourlyWeatherEntry>,
        val lat: Double,
        val lon: Double,
        val minutely: List<Minutely>,
        val timezone: String,
        @SerializedName("timezone_offset")
        val timezoneOffset: Int
)