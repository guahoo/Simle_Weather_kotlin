package com.simple_weather.network

import androidx.lifecycle.LiveData
import com.simple_weather.data.db.entity.current.CurrentWeatherEntry
import com.simple_weather.network.networkresponse.futureWeatherResponse.DailyResponse
import com.simple_weather.network.networkresponse.futureWeatherResponse.FutureWeatherResponse

interface WeatherDataSourse {
    val downloadedCurrentWeather : LiveData<CurrentWeatherEntry>
    val downloadedFutureWeatherResponse : LiveData<FutureWeatherResponse>
    val downloadedDailyWeatherResponse : LiveData<DailyResponse>

    suspend fun fetchCurrentWeatherByCityName(
        location: String,
        languageCode: String
    )

    suspend fun fetchCurrentWeatherByCoord(
        locationLat: String,
        locationLon: String,
        languageCode: String
    )

//    suspend fun fetchFutureWeatherByCoord(
//        locationLat: String,
//        locationLon: String,
//        languageCode: String
//    )

    suspend fun fetchDailyWeatherByCoord(
        locationLat: String,
        locationLon: String,
        languageCode: String
    )
    }

