package com.simple_weather.data.db.entity.future.futureDaily


import com.google.gson.annotations.SerializedName

data class WeatherX(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)