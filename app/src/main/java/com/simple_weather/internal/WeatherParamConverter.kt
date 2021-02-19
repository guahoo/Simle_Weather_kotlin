package com.simple_weather.internal

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


const val LONGDATETIMEPATTERN = "HH:mm dd.MM EEEE"
const val MIDDLEDATETIMEPATTERN = "dd.MM EEEE"
const val SHORDATEPATTERN = "dd.MM"
const val SHORTTIMEPATTERN = "HH:mm"

class WeatherParamConverter {

    fun convertDateTime(dateTime: Long, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault())
                .format(Date(dateTime * 1000)).toUpperCase(Locale.getDefault())
    }

    fun convertTemp(s: Double): String {
        val c = s.toInt()
        return ("$c°C")
    }

     fun convertWindDirection(wind: Int): String {

        var windDirection = ""


        when (wind) {
            in 0..9 -> {
                windDirection = "N"
            }
            in 351..359 -> {
                windDirection = "N"
            }
            in 81..99 -> {
                windDirection = "E"
            }
            in 171..189 -> {
                windDirection = "S"
            }
            in 261..279 -> {
                windDirection = "W"
            }
            in 10..80 -> {
                windDirection = "NE"
            }
            in 100..170 -> {
                windDirection = "SE"
            }
            in 190..260 -> {
                windDirection = "SW"
            }
            in 280..350 -> {
                windDirection = "NW"
            }

        }
        return windDirection

    }

}