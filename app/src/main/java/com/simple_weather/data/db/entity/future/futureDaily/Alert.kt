package com.simple_weather.data.db.entity.future.futureDaily


import com.google.gson.annotations.SerializedName

data class Alert(
    val description: String,
    val end: Int,
    val event: String,
    @SerializedName("sender_name")
    val senderName: String,
    val start: Int
)