package com.simple_weather.internal

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object LocalDateConverter {
@TypeConverter
@JvmStatic
fun stringToDate(str:String?)=str?.let{
    LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
}

}