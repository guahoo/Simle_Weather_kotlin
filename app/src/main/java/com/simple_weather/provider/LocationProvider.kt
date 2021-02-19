package com.simple_weather.provider

interface LocationProvider {
   // suspend fun hasLocationChanged(weatherLocation: Coord): Boolean
    suspend fun getPreferredLocationStringOrCoords(): Array<String>
    fun getLocationName():String?
    fun isUsingDeviceLocation(): Boolean
}