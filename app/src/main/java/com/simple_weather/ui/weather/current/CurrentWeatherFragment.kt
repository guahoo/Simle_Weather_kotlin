package com.simple_weather.ui.weather.current

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.simple_weather.R

import com.simple_weather.internal.LONGDATETIMEPATTERN
import com.simple_weather.internal.SHORTTIMEPATTERN
import com.simple_weather.internal.WeatherIconMap
import com.simple_weather.internal.WeatherParamConverter
import com.simple_weather.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance



const val USE_DEVISE_LOCATION = "USE_DEVISE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"
const val GEOCODER_LOCALITY_NAME = "GEOCODER_LOCALITY_NAME"
const val GEOCODER_AREA_NAME = "GEOCODER_AREA_NAME"

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()
    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var locality: String
    private lateinit var area: String
    private lateinit var preferences: SharedPreferences



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(CurrentWeatherViewModel::class.java)
        val context = context?.applicationContext
        preferences = PreferenceManager.getDefaultSharedPreferences(context)


        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()


        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            loadingWidgetGroup.visibility = View.GONE
            weatherDisplayLayout.visibility = View.VISIBLE
            errorMessageShow(isErrorsCatching(it.name))
            weatherShow(isErrorsCatching(it.name))


            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            locality = preferences.getString(GEOCODER_LOCALITY_NAME, "")

            area = preferences.getString(GEOCODER_AREA_NAME, "")

            updateLocation(locality,area)

            if (preferences.getBoolean(USE_DEVISE_LOCATION, true)) updateLocation(locality) else updateLocation(locality,area)
//            if (preferences.getBoolean(USE_DEVISE_LOCATION, true) && area!=null) updateLocation(locality,area)



            setTextSizeTvCityName()
            updateCurrentTimeDate(it.dt)
            updateCurrentTemp(it.main_temp)
            updateFeelsLikeTemp(it.main_feelsLike)
            updateTimeSunsetSunRise(it.sys_sunrise, it.sys_sunset)
            updateStatusWeatherView(it.weather)
            updateWeatherDescription(it.weather)
            updateWindWeatherView(it.wind_deg, it.wind_speed)
            updateHumidity(it.main_humidity)
            updatePressure(it.main_pressure)
            updateVisibility(it.visibility)
            setImageViewsColor(true)


        })
    }

    private fun isErrorsCatching(cityName: String): Boolean {
        return cityName=="Town Zero"
    }

    private fun errorMessageShow(isErrorCatching: Boolean){
        errorsWidgetGroup.visibility =  when {
            isErrorCatching -> {
                View.VISIBLE
            }
            else -> View.GONE
        }
    }

    private fun weatherShow(isErrorCatching: Boolean){
        weatherDisplayLayout.visibility =  when {
            !isErrorCatching -> {
                View.VISIBLE
            }
            else -> View.GONE
        }
    }

    private fun updateLocation(location: String){
        tv_cityName.text = location
    }

    private fun updateLocation(location: String, area: String){
        tv_cityName.text = location
        tv_areaName.text = area
    }

    private fun updateCurrentTimeDate(dateTime: Long){
       tv_time.text = WeatherParamConverter().convertDateTime(dateTime, LONGDATETIMEPATTERN)
    }

    private fun updateTimeSunsetSunRise(sunriseTime: Long, sunsetTime: Long){
        tv_sunrise_time.text = WeatherParamConverter().convertDateTime(sunriseTime, SHORTTIMEPATTERN)
        tv_sunset_time.text = WeatherParamConverter().convertDateTime(sunsetTime, SHORTTIMEPATTERN)
    }

    private fun updateCurrentTemp(currentTemp: Double){
        tv_temp.text = WeatherParamConverter().convertTemp(currentTemp)

    }

    private fun updateFeelsLikeTemp(feelsLike: Double){
        tv_feelsLike.text = "Ощущается как ${WeatherParamConverter().convertTemp(feelsLike)}"
    }

    private fun updateStatusWeatherView(weather: String){
       iv_weatherStatus!!.setImageResource(WeatherIconMap.getResourceIdent(
               JSONArray(weather).getJSONObject(0).get("main") as String?))
    }

    private fun updateWindWeatherView(windDeg:Int, windSpeed:Double){
        tv_windDirection.text = WeatherParamConverter().convertWindDirection(windDeg)
        tv_windSpeed.text = "$windSpeed m/s "
    }

    private fun updateWeatherDescription(weather: String){
        tv_weather_description.text = JSONArray(weather).getJSONObject(0).get("description") as String?
    }

    private fun updateHumidity(humidity: Double){
       tv_humidity_value.text = "${humidity.toInt()} %"
    }

    private fun updatePressure(pressure: Double){
       tv_pressure_value.text = "${pressure.toInt()} kPa"
    }
    private fun updateVisibility(visibility: Int){
       tv_visibility_value.text = "${visibility} m"
    }

    private fun setImageViewsColor(night: Boolean) {
        val imageColor = if (night) R.color.white else R.color.black
            iv_weatherStatus.setColorFilter(ContextCompat.getColor(context?.applicationContext!!, imageColor))

    }

    private fun setTextSizeTvCityName() {
        val displayedCityName = tv_cityName.text
        val spaces = displayedCityName?.length ?: 0
        if (spaces >= 20) {
            tv_cityName!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        }
    }
}