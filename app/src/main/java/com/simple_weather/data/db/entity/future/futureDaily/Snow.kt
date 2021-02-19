package com.simple_weather.data.db.entity.future.futureDaily


import com.google.gson.annotations.SerializedName

data class Snow(
    @SerializedName("1h")
    val h: Double
)