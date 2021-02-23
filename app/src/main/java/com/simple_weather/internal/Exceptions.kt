package com.simple_weather.internal

import java.io.IOException

class NoConnectivityException : IOException()
class LocationPermissionNotGrantedException : IOException(){
    override fun printStackTrace() {
    }


}