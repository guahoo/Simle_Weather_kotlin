package com.simple_weather.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.simple_weather.data.db.entity.future.WeatherX

class ListTypeConvertersFutureWeather {
    var gson = Gson()
    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<WeatherX> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<WeatherX?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<WeatherX?>?): String {
        return gson.toJson(someObjects)
    }
}