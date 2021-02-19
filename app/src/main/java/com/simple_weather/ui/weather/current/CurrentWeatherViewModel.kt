package com.simple_weather.ui.weather.current

import androidx.lifecycle.ViewModel
import com.simple_weather.data.repository.ForecastRepository
import com.simple_weather.internal.lazyDeferred

class CurrentWeatherViewModel(
        private val forecastRepository: ForecastRepository
) : ViewModel() {
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }

}