package com.simple_weather.ui.weather.future.list

import androidx.lifecycle.ViewModel
import com.simple_weather.data.repository.ForecastRepository
import com.simple_weather.internal.lazyDeferred

class FutureListWeatherViewModel(private val forecastRepository: ForecastRepository) : ViewModel() {

        val weatherEntries by lazyDeferred {
            forecastRepository.getFutureWeather()
        }
}