package com.simple_weather.ui.weather.future.daily

import androidx.lifecycle.ViewModel
import com.simple_weather.data.repository.ForecastRepository
import com.simple_weather.internal.lazyDeferred

class DailyListWeatherViewModel(private val forecastRepository: ForecastRepository) : ViewModel() {

        val weatherEntries by lazyDeferred {
            forecastRepository.getDailyWeather()
        }
}