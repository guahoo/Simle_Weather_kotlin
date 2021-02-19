package com.simple_weather.data.db.entity.future.futureDaily


import com.google.gson.annotations.SerializedName

data class Minutely(
    val dt: Long,
    val precipitation: Double
)