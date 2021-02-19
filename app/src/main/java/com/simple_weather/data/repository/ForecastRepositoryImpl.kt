package com.simple_weather.data.repository


import android.util.Log
import androidx.lifecycle.LiveData
import com.simple_weather.Provider.LocationProvider
import com.simple_weather.data.db.*
import com.simple_weather.data.db.entity.current.ConditionCurrentWeatherEntry
import com.simple_weather.data.db.entity.current.CurrentWeatherEntry
import com.simple_weather.data.db.FutureDailyDao
import com.simple_weather.data.db.entity.future.forecastByHours.ConditionFutureWeatherEntry
import com.simple_weather.data.db.entity.future.futureDaily.ConditionDailyWeather
import com.simple_weather.network.WeatherDataSourse
import com.simple_weather.network.networkresponse.futureWeatherResponse.DailyResponse
import com.simple_weather.network.networkresponse.futureWeatherResponse.FutureWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
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
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchCurrentWeather(newCurrentWeather)
            }
//            downloadedFutureWeatherResponse.observeForever { newFutureWeather ->
//                persistFetchFutureWeather(newFutureWeather)
//
//            }
            downloadedDailyWeatherResponse.observeForever { newFutureWeather ->
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
            fetchCurrentWeather()
            fetchDailyWeather()

        }

    }



    private fun persistFetchDailyWeather(fetchedWeatherResponse: DailyResponse) {
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

                futureDailyDao.insert(dailyWeatherList)
                futureWeatherDao.insert(hourlyWeatherList)
            }
        } catch (e: Exception) {
            Log.e("111", "Ex")
        }
    }

    private suspend fun fetchDailyWeather() {
        val deviceLocationLat: String
        val deviceLocationLon: String
        if (locationProvider.isUsingDeviceLocation() && locationProvider.getPreferredLocationStringOrCoords().size > 1) {
            deviceLocationLat = locationProvider.getPreferredLocationStringOrCoords()[0]
            deviceLocationLon = locationProvider.getPreferredLocationStringOrCoords()[1]

            weatherDataSourse.fetchDailyWeatherByCoord(
                deviceLocationLat, deviceLocationLon,
                Locale.getDefault().language
            )
        }
    }

    private fun persistFetchCurrentWeather(fetchedWeather: CurrentWeatherEntry) {
        try {

            GlobalScope.launch(Dispatchers.IO) {
                fetchedWeather.name = locationProvider.getLocationName().toString()
                currentWeatherDao.upsert(fetchedWeather)

            }
        } catch (e: Exception) {
            Log.e("111", "Ex")
        }
    }

    private suspend fun fetchCurrentWeather() {
        val deviceLocationLat: String
        val deviceLocationLon: String
        if (locationProvider.isUsingDeviceLocation() && locationProvider.getPreferredLocationStringOrCoords().size > 1) {
            deviceLocationLat = locationProvider.getPreferredLocationStringOrCoords()[0]
            deviceLocationLon = locationProvider.getPreferredLocationStringOrCoords()[1]
            weatherDataSourse.fetchCurrentWeatherByCoord(
                    deviceLocationLat, deviceLocationLon,
                    Locale.getDefault().language
            )
        } else {
            weatherDataSourse.fetchCurrentWeatherByCityName(
                    locationProvider.getPreferredLocationStringOrCoords()[0],
                    Locale.getDefault().language
            )
        }
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }


}

