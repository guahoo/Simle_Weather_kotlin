package com.simple_weather.data.db.entity.weather_entry

import androidx.room.TypeConverters

@TypeConverters(TypeConverters::class)
data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)