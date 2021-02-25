import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.simple_weather.internal.LocationPermissionNotGrantedException
import com.simple_weather.internal.asDeferred
import com.simple_weather.provider.LocationProvider
import com.simple_weather.provider.PreferencesProvider
import com.simple_weather.ui.weather.current.GEOCODER_AREA_NAME
import com.simple_weather.ui.weather.current.GEOCODER_LOCALITY_NAME

import kotlinx.coroutines.Deferred
import java.util.*

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
        private val fusedLocationProviderClient: FusedLocationProviderClient,
        context: Context
) : PreferencesProvider(context), LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: String): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            false
        }

        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPreferredLocationString(): Array<String> {
        return if (isUsingDeviceLocation()) {
            try {
                val deviceLocation = getLastDeviceLocation().await()
                //?: return "${getCustomLocationName()}"
                println("${deviceLocation?.latitude},${deviceLocation?.longitude}")
                arrayOf(deviceLocation?.latitude.toString(), deviceLocation?.longitude.toString())

            } catch (e: LocationPermissionNotGrantedException) {
                arrayOf("${getCustomLocationName()}")
            }
        }
        else
            getLocationCoord(preferences.getString(CUSTOM_LOCATION,"London"))
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: String): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
                ?: return false

        // Comparing doubles cannot be done with "=="
        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - 40.131313) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - 50.2131121) > comparisonThreshold
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: String): Boolean {
        if (!isUsingDeviceLocation()) {
            val customLocationName = getCustomLocationName()
            return customLocationName != lastWeatherLocation
        }
        return false
    }

    override fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?> {
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(appContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun getLocationCoord(location:String?):Array<String>{
        val geoCoder = Geocoder(context, Locale.getDefault())
        val adress = geoCoder.getFromLocationName(location,1)
        val latitude = adress[0].latitude.toString()
        val longitude = adress[0].longitude.toString()
        getLocationName(adress[0].latitude,adress[0].longitude)


        return arrayOf(latitude,longitude)
        //println( "locationCoord ${longitude.toString()+latitude.toString()}")
    }
     override fun getLocationName(latitude:Double,longitude:Double):String {

        val geoCoder = Geocoder(context, Locale.getDefault())
        val listAddresses = geoCoder.getFromLocation(latitude, longitude, 1)
//        val location = try{
//            listAddresses[0].getAddressLine(0)
//        } catch (aE:ArrayIndexOutOfBoundsException){
//            println(aE.message)
//        }

        if (listAddresses.size > 0) {
            if (listAddresses[0].locality.length != null) {
                preferences.edit().putString(GEOCODER_LOCALITY_NAME, listAddresses[0].locality).apply()
                preferences.edit().putString(GEOCODER_AREA_NAME, "${listAddresses[0].subAdminArea}, ${listAddresses[0].adminArea}").apply()

            } else {
                preferences.edit().putString(GEOCODER_LOCALITY_NAME, listAddresses[0].subAdminArea).apply()
                preferences.edit().putString(GEOCODER_AREA_NAME, listAddresses[0].adminArea).apply()
            }
        }
         return listAddresses[0].locality?:listAddresses[0].subAdminArea
    }
}