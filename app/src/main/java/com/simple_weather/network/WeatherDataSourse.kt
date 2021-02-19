package com.simple_weather.network

import androidx.lifecycle.LiveData
import com.simple_weather.network.networkresponse.WeatherAllInOneResponse

interface WeatherDataSourse {
    val downloadedWeatherAllInOneWeatherResponse : LiveData<WeatherAllInOneResponse>
    suspend fun fetchWeatherByCoord(
        locationLat: String,
        locationLon: String,
        languageCode: String
    )
    }

