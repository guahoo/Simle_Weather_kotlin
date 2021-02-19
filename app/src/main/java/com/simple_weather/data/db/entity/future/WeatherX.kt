package com.simple_weather.data.db.entity.future

import androidx.room.TypeConverters

@TypeConverters(TypeConverters::class)
data class WeatherX(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)