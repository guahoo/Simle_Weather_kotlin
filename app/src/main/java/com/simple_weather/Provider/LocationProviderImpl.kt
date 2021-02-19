package com.simple_weather.Provider

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.simple_weather.data.db.entity.current.Coord
import com.simple_weather.internal.LocationPermissionNotGrantedException
import com.simple_weather.internal.asDeferred
import kotlinx.coroutines.Deferred
import java.util.*
import kotlin.math.abs


const val USE_DEVISE_LOCATION = "USE_DEVISE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"
const val GEOCODER_LOCALITY_NAME = "GEOCODER_LOCALITY_NAME"
const val GEOCODER_AREA_NAME = "GEOCODER_AREA_NAME"

class LocationProviderImpl(
        private val fusedLocationProviderClient: FusedLocationProviderClient,
        context: Context) : LocationProvider, PreferencesProvider(context) {

    override suspend fun hasLocationChanged(weatherLocation: Coord): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(weatherLocation)

        } catch (e: LocationPermissionNotGrantedException) {
            println("""Location${weatherLocation.lat}""")
            false
        }
        println("""Location${weatherLocation.lat}""")
        //return deviceLocationChanged||hasCustomLocationChanged(weatherLocation)
        return true
    }

    private fun hasCustomLocationChanged(weatherLocation: Coord): Boolean {
        val customLocationName = getCustomLocationName()
        return true
    }

    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, "")
    }


    override suspend fun getPreferredLocationStringOrCoords(): Array<String> {
        if (isUsingDeviceLocation()) {
            try {
                val deviceLocation = getLastDeviceLocationAsync().await()
                        ?: return arrayOf("${getCustomLocationName()}")
                println("lat=${deviceLocation.latitude}" + " " + "lon=${deviceLocation.longitude}")
                getLocationName(deviceLocation.latitude, deviceLocation.longitude)
                //getLocationName(55.1973, 40.3732)
                return arrayOf("${deviceLocation.latitude}", "${deviceLocation.longitude}")


            } catch (e: LocationPermissionNotGrantedException) {
                return arrayOf(preferences.getString(CUSTOM_LOCATION, ""))
            }
        } else {
            return arrayOf(preferences.getString(CUSTOM_LOCATION, ""))
        }
    }

    override fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVISE_LOCATION, true)
    }

    private suspend fun hasDeviceLocationChanged(weatherLocation: Coord): Boolean {
        if (isUsingDeviceLocation()) {
            return preferences.getBoolean(USE_DEVISE_LOCATION, false)
        }

        val deviceLocation = getLastDeviceLocationAsync().await()
                ?: return false

        val comparisonTreshold = 0.03
        return abs(deviceLocation.latitude - weatherLocation.lat) > comparisonTreshold &&
                return abs(deviceLocation.longitude - weatherLocation.lon) > comparisonTreshold

    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocationAsync(): Deferred<Location?> {

        return if (hasLocationPermission()) fusedLocationProviderClient.lastLocation.asDeferred()
        else throw LocationPermissionNotGrantedException()
    }

    private fun hasLocationPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    private fun getLocationName(lat: Double, lon: Double) {
        val geoCoder = Geocoder(context, Locale.getDefault())
        //try {
        val listAddresses = geoCoder.getFromLocation(lat, lon, 1)
        //if (null != listAddresses && listAddresses.size > 0) {
        val location = listAddresses[0].getAddressLine(0)

        if (listAddresses[0].locality != null) {
            preferences.edit().putString(GEOCODER_LOCALITY_NAME, listAddresses[0].locality).apply()
            preferences.edit().putString(GEOCODER_AREA_NAME, "${listAddresses[0].subAdminArea}, ${listAddresses[0].adminArea}").apply()
        } else {
            preferences.edit().putString(GEOCODER_LOCALITY_NAME, listAddresses[0].subAdminArea).apply()
            preferences.edit().putString(GEOCODER_AREA_NAME, listAddresses[0].adminArea).apply()
        }





                // val cityName = listAddresses[0].locality

//                 listAddresses[0].locality
//                val area = listAddresses[0].adminArea
                // город $cityName район $area
                println("адрес $location ")

                listAddresses[0].locality
           // }
//        } catch (e: IOException) {
//            e.printStackTrace()
//            return "Town Zero"
//
//        }
    }

    override fun getLocationName():String?{
        return if (isUsingDeviceLocation()) preferences.getString(GEOCODER_LOCALITY_NAME,"Town Zero")
        else preferences.getString(CUSTOM_LOCATION,"Town Zero")
    }
}





