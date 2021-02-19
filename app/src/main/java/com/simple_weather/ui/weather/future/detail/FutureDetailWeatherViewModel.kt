package com.simple_weather.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.simple_weather.data.repository.ForecastRepository
import com.simple_weather.internal.lazyDeferred

class FutureDetailWeatherViewModel(
        private val date: Long,
        private val forecastRepository: ForecastRepository
) : ViewModel() {
    val weatherEntries by lazyDeferred {
        forecastRepository.getFutureWeatherByDt(date)
    }
}