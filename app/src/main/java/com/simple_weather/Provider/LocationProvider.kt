package com.simple_weather.Provider

import com.simple_weather.data.db.entity.current.Coord

interface LocationProvider {
    suspend fun hasLocationChanged(weatherLocation: Coord): Boolean
    suspend fun getPreferredLocationStringOrCoords(): Array<String>
    fun getLocationName():String?
    fun isUsingDeviceLocation(): Boolean
}