package com.simple_weather.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.simple_weather.data.db.entity.current.Weather

class ListTypeConvertersCurrentWeather {

    var gson = Gson()
    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Weather> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Weather?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Weather?>?): String {
        return gson.toJson(someObjects)
    }
}