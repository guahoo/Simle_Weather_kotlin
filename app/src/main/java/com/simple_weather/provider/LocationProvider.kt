package com.simple_weather.provider

interface LocationProvider {
   // suspend fun hasLocationChanged(weatherLocation: Coord): Boolean
    //suspend fun getPreferredLocationCoords(): Array<String>
    //fun getLocationName():String?
    fun isUsingDeviceLocation(): Boolean
    //suspend fun getLocationName()
    suspend fun hasLocationChanged(lastWeatherLocation: String):Boolean
    suspend fun getPreferredLocationString():Array<String>
    fun getLocationName(latitude:Double,longitude:Double):String
}