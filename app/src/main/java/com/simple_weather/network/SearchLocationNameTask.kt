package com.simple_weather.network

import android.os.AsyncTask
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList


private const val CITY_NAME_DISPLAY_LIST = "name"
private const val DISPLAYED_NAME = "display_name"
class SearchLocationNameTask(var getCityName: String) : AsyncTask<String?, Void?, String>() {

    private var locale = Locale.getDefault()
    private val cityLon = ArrayList<HashMap<String, String>>()
    private val cityLat = ArrayList<HashMap<String, String>>()




    public override fun onPostExecute(result: String) {

        try {
            val geoData = JSONArray(result)
            for (i in 0 until geoData.length()) {

//                val getCityName = HashMap<String, String?>()
//                val getCityLon = HashMap<String, String>()
//                val getCityLat = HashMap<String, String>()
//                getCityName[CITY_NAME_DISPLAY_LIST] = placeInfo.getString(DISPLAYED_NAME)
//               // println("location"+placeInfo.getString(DISPLAYED_NAME))
//                cityNames.add(getCityName)
//                cityLon.add(getCityLon)
//                cityLat.add(getCityLat)



            }
        } catch (e: Exception){
            println(e.message)
        }




    }


    companion object {
        const val URL_REQUEST_FORECAST = "https://nominatim.openstreetmap.org/search?city=%s&format=json&place=city&accept-language=%s&limit=5"
    }

    override fun doInBackground(vararg params: String?): String {
        return NominativeConnect.executeGet(String.format(URL_REQUEST_FORECAST, getCityName, locale))!!
    }


}
