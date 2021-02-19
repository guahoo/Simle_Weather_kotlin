package com.simple_weather.internal


import com.simple_weather.R
import java.util.*

object WeatherIconMap {
    private val weather_icons_map: MutableMap<String?, Int> = HashMap()
    private const val OVERCAST_CLOUDS = "Clouds"
    private const val CLEAR_SKY = "clear sky"
    private const val CLEAR = "Clear"
    private const val SHOWER_RAIN = "Rain"
    private const val SHOWER_RAIN_ALERT = "Rain"
    private const val LIGHT_RAIN_ALERT = "Rain"
    private const val RAIN_ALERT = "rain_alert"
    private const val RAIN = "Rain"
    private const val LIGHT_RAIN = "Rain"
    private const val SCATTERED_CLOUD = "Clouds"
    private const val FEW_CLOUDS = "Clouds"
    private const val BROKEN_CLOUDS = "Clouds"
    private const val SNOW = "Snow"
    private const val SNOW_ALERT = "Snow"
    private const val LIGHT_SNOW = "Snow"
    private const val HEAVY_SNOW = "Snow"
    private const val LIGHT_SHOWER_SNOW = "Snow"
    private const val LIGHT_INTENSITY_DRIZZLE = "Drizzle"
    private const val MIST = "Mist"
    private const val DRIZZLE = "Drizzle"
    private const val FOG = "Fog"


    fun getResourceIdent(weatherModel: String?): Int {
        return weather_icons_map[if (weather_icons_map.containsKey(weatherModel)) weatherModel else null]!!
    }

    init {
        weather_icons_map[OVERCAST_CLOUDS] = R.drawable.ic_cloud
        weather_icons_map[CLEAR_SKY] = R.drawable.ic_sun
        weather_icons_map[CLEAR] = R.drawable.ic_sun
        weather_icons_map[SHOWER_RAIN] = R.drawable.ic_rain
        weather_icons_map[SHOWER_RAIN_ALERT] = R.drawable.ic_rain_alert
        weather_icons_map[LIGHT_RAIN_ALERT] = R.drawable.ic_rain_alert
        weather_icons_map[RAIN_ALERT] = R.drawable.ic_rain_alert
        weather_icons_map[RAIN] = R.drawable.ic_rain
        weather_icons_map[LIGHT_RAIN] = R.drawable.ic_rain_alt_sun
        weather_icons_map[SCATTERED_CLOUD] = R.drawable.ic_cloud
        weather_icons_map[FEW_CLOUDS] = R.drawable.ic_cloud
        weather_icons_map[BROKEN_CLOUDS] = R.drawable.ic_cloud_sun
        weather_icons_map[SNOW] = R.drawable.ic_snow_alt
        weather_icons_map[HEAVY_SNOW] = R.drawable.ic_snow_alt
        weather_icons_map[SNOW_ALERT] = R.drawable.ic_snow_alert_01
        weather_icons_map[LIGHT_SNOW] = R.drawable.ic_snow_alt
        weather_icons_map[LIGHT_SHOWER_SNOW] = R.drawable.ic_snow_alt
        weather_icons_map[LIGHT_INTENSITY_DRIZZLE] = R.drawable.ic_cloud_sun
        weather_icons_map[MIST] = R.drawable.ic_fog
        weather_icons_map[DRIZZLE] = R.drawable.ic_fog
        weather_icons_map[FOG] = R.drawable.ic_fog
        weather_icons_map[null] = R.drawable.ic_umbrella
    }
}