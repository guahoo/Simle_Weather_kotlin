package com.simple_weather.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simple_weather.data.repository.ForecastRepository
import com.simple_weather.ui.weather.current.CurrentWeatherViewModel

class FutureDetailWeatherViewModelFactory (
        private val forecastRepository: ForecastRepository,
        private val date:Long

): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureDetailWeatherViewModel(date,forecastRepository) as T
    }
}