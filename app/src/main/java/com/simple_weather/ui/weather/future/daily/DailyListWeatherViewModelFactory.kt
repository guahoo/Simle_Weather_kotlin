package com.simple_weather.ui.weather.future.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simple_weather.data.repository.ForecastRepository

class  DailyListWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val cityName: String,
    private val date: Long
    ): ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DailyListWeatherViewModel(forecastRepository,cityName,date) as T
        }
    }

