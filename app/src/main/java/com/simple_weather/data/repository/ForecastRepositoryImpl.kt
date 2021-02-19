package com.simple_weather.data.repository


import android.util.Log
import androidx.lifecycle.LiveData
import com.simple_weather.provider.LocationProvider
import com.simple_weather.data.db.*
import com.simple_weather.data.db.entity.weather_entry.ConditionCurrentWeatherEntry
import com.simple_weather.data.db.FutureDailyDao
import com.simple_weather.data.db.entity.weather_entry.ConditionFutureWeatherEntry
import com.simple_weather.data.db.entity.weather_entry.ConditionDailyWeather
import com.simple_weather.network.WeatherDataSourse
import com.simple_weather.network.networkresponse.WeatherAllInOneResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherDataSourse: WeatherDataSourse,
    private val futureDailyDao: FutureDailyDao,
    private val locationProvider: LocationProvider



) : ForecastRepository {
    init {

        weatherDataSourse.apply {
            downloadedWeatherAllInOneWeatherResponse.observeForever { newFutureWeather ->
               persistFetchDailyWeather(newFutureWeather)
            }
        }

    }


    override suspend fun getCurrentWeather(): LiveData<out ConditionCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getCurrentWeather()
        }
    }

    override suspend fun getFutureWeather(): LiveData<out List<ConditionFutureWeatherEntry>> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext futureWeatherDao.getFutureWeather()
        }
    }



    override suspend fun getFutureWeatherByDt(dt: Long): LiveData<out ConditionFutureWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext futureWeatherDao.getDetailedFutureWeather(dt)
        }
    }

    override suspend fun getDailyWeather(): LiveData<out List<ConditionDailyWeather>> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext futureDailyDao.getDailyWeather()
        }
    }


    private suspend fun initWeatherData() {
        val lastWeatherLocation = currentWeatherDao.getCurrentWeather().value
        if (lastWeatherLocation == null) {
            fetchWeather()

        }

    }



    private fun persistFetchDailyWeather(fetchedWeatherResponse: WeatherAllInOneResponse) {
        try {

            GlobalScope.launch(Dispatchers.IO) {
                val dailyWeatherList = fetchedWeatherResponse.daily.toList()
                val hourlyWeatherList = fetchedWeatherResponse.hourly.toList()
                (dailyWeatherList.indices).forEach{
                    dailyWeatherList[it].cityName = locationProvider.getLocationName().toString()
                }

                (hourlyWeatherList.indices).forEach{
                    hourlyWeatherList[it].cityName = locationProvider.getLocationName().toString()
                }

                val currentWeather = fetchedWeatherResponse.current

                futureDailyDao.insert(dailyWeatherList)
                futureWeatherDao.insert(hourlyWeatherList)

                currentWeather.name = locationProvider.getLocationName().toString()
                currentWeatherDao.upsert(currentWeather)
            }
        } catch (e: Exception) {
            Log.e("111", "Ex")
        }
    }

    private suspend fun fetchWeather() {
        val deviceLocationLat: String
        val deviceLocationLon: String
        if (locationProvider.isUsingDeviceLocation() && locationProvider.getPreferredLocationStringOrCoords().size > 1) {
            deviceLocationLat = locationProvider.getPreferredLocationStringOrCoords()[0]
            deviceLocationLon = locationProvider.getPreferredLocationStringOrCoords()[1]

            weatherDataSourse.fetchWeatherByCoord(
                deviceLocationLat, deviceLocationLon,
                Locale.getDefault().language
            )
        }
    }
}

