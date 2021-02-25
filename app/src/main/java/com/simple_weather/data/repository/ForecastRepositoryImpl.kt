package com.simple_weather.data.repository


import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import com.simple_weather.data.db.*
import com.simple_weather.data.db.entity.weather_entry.ConditionCurrentWeatherEntry
import com.simple_weather.data.db.entity.weather_entry.ConditionDailyWeather
import com.simple_weather.data.db.entity.weather_entry.ConditionFutureWeatherEntry
import com.simple_weather.network.WeatherDataSourse
import com.simple_weather.network.networkresponse.WeatherAllInOneResponse
import com.simple_weather.provider.LocationProvider
import com.simple_weather.ui.settings.SettingsFragment
import kotlinx.coroutines.*
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.util.*

private lateinit var lastUpdate: ZonedDateTime

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

    override suspend fun getDailyWeather(loc: String,date: Long): LiveData<out List<ConditionDailyWeather>> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext futureDailyDao.getDailyWeatherByLoc(loc, date)
        }
    }


    private suspend fun initWeatherData() {
        val lastWeatherLocation: Long = if (currentWeatherDao.getCurrentWeatherNonLive()!=null) {
            currentWeatherDao.getCurrentWeatherNonLive()!!.dt*1000
        } else{
            0
        }
        println("location ${currentWeatherDao.getCurrentWeather().value.toString()}")

        if (lastWeatherLocation == null) {

            fetchWeather()

        }
        if (isFetchCurrentNeeded(ZonedDateTime.ofInstant(Instant.ofEpochMilli(lastWeatherLocation), ZoneId.systemDefault()))) {
            fetchWeather()
        }

    }


    private fun persistFetchDailyWeather(fetchedWeatherResponse: WeatherAllInOneResponse) {

        try {

            GlobalScope.launch(Dispatchers.IO) {
                val dailyWeatherList = fetchedWeatherResponse.daily.toList()
                val hourlyWeatherList = fetchedWeatherResponse.hourly.toList()
                val currentWeather = fetchedWeatherResponse.current


//
                val locationName = locationProvider.getLocationName(locationProvider.getPreferredLocationString()[0].toDouble(),
                        locationProvider.getPreferredLocationString()[1].toDouble())

                currentWeather.name = locationName

                (dailyWeatherList.indices).forEach {
                    dailyWeatherList[it].cityName = locationName
                }

                (hourlyWeatherList.indices).forEach {
                    hourlyWeatherList[it].cityName = locationName
                }





                currentWeatherDao.upsert(currentWeather)
                futureDailyDao.insert(dailyWeatherList)
                futureWeatherDao.insert(hourlyWeatherList)


            }
        } catch (e: Exception) {
            Log.e("111", "Ex")
        }
    }

    private suspend fun fetchWeather() {

try {
    val deviceLocationLat: String = locationProvider.getPreferredLocationString()[0]
    val deviceLocationLon: String = locationProvider.getPreferredLocationString()[1]

    weatherDataSourse.fetchWeatherByCoord(
            deviceLocationLat, deviceLocationLon,
            Locale.getDefault().language
    )
} catch (aE: retrofit2.HttpException){

    println(aE.message)
}



    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(0)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}

