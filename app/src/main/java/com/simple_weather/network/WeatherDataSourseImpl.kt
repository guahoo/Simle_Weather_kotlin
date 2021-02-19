package com.simple_weather.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.simple_weather.internal.NoConnectivityException
import com.simple_weather.network.networkresponse.WeatherAllInOneResponse

class WeatherDataSourseImpl(private val OpenWeatherApiService: OpenWeatherApiService) : WeatherDataSourse {
    private val _downloadedDailyWeather = MutableLiveData<WeatherAllInOneResponse>()
    override val downloadedWeatherAllInOneWeatherResponse: LiveData<WeatherAllInOneResponse>
        get() = _downloadedDailyWeather

    override suspend fun fetchWeatherByCoord(locationLat: String, locationLon: String, languageCode: String) {
        try {
            val fetchCurrentWeather = OpenWeatherApiService
                    .getFutureWeatherDailyAsync(locationLat, locationLon, languageCode)
                    .await()
            _downloadedDailyWeather.postValue(fetchCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet Connection", e)
        }
    }


}