package com.simple_weather.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.simple_weather.data.db.entity.current.*
import com.simple_weather.internal.NoConnectivityException
import com.simple_weather.network.networkresponse.futureWeatherResponse.DailyResponse
import com.simple_weather.network.networkresponse.futureWeatherResponse.FutureWeatherResponse

class WeatherDataSourseImpl (private val OpenWeatherApiService:OpenWeatherApiService) : WeatherDataSourse {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherEntry>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherEntry>
        get() = _downloadedCurrentWeather




    override suspend fun fetchCurrentWeatherByCityName(location: String, languageCode: String) {
       try{
           val fetchCurrentWeather = OpenWeatherApiService
               .getCurrentWeatherAsync(location,languageCode)
               .await()
           _downloadedCurrentWeather.postValue(fetchCurrentWeather)
       }catch (e: NoConnectivityException){
           Log.e("Connectivity","No internet Connection", e)
       }catch (e: retrofit2.HttpException){
           val fetchCurrentWeather = CurrentWeatherEntry(base="stations", clouds= Clouds(all=20), cod=200, coord= Coord(lat=55.0794, lon=38.7783), dt=1612771436, id=546230, main= Main(feelsLike=-21.79, humidity=78, pressure=1020, temp=-17.49, tempMax=-17.0, tempMin=-18.0), name="Town Zero", sys= Sys(country="RU", id=9020, sunrise=1612760559, sunset=1612793734, type=1), timezone=10800, visibility=10000, weather= listOf(Weather(description="few clouds", icon="02d", id=801, main="Clouds")), wind= Wind(deg=190, speed=1.0))
           _downloadedCurrentWeather.postValue(fetchCurrentWeather)
           Log.e("Connectivity","No internet Connection", e)
       }
    }

    override suspend fun fetchCurrentWeatherByCoord(locationLat: String, locationLon: String, languageCode: String) {
        try{
            val fetchCurrentWeather = OpenWeatherApiService
                    .getCurrentWeatherAsyncByCoordsAsync(locationLat,locationLon,languageCode)
                    .await()
            _downloadedCurrentWeather.postValue(fetchCurrentWeather)
        }catch (e: NoConnectivityException){
            Log.e("Connectivity","No internet Connection", e)
        }catch (e: retrofit2.HttpException){
            val fetchCurrentWeather = CurrentWeatherEntry(base="stations", clouds= Clouds(all=20), cod=200, coord= Coord(lat=55.0794, lon=38.7783), dt=1612771436, id=546230, main= Main(feelsLike=-21.79, humidity=78, pressure=1020, temp=-17.49, tempMax=-17.0, tempMin=-18.0), name="Town Zero", sys= Sys(country="RU", id=9020, sunrise=1612760559, sunset=1612793734, type=1), timezone=10800, visibility=10000, weather= listOf(Weather(description="few clouds", icon="02d", id=801, main="Clouds")), wind= Wind(deg=190, speed=1.0))
            _downloadedCurrentWeather.postValue(fetchCurrentWeather)
            Log.e("Connectivity","No internet Connection", e)
        }
    }



    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeatherResponse: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    private val _downloadedDailyWeather = MutableLiveData<DailyResponse>()
    override val downloadedDailyWeatherResponse: LiveData<DailyResponse>
        get() = _downloadedDailyWeather

//    override suspend fun fetchFutureWeatherByCoord(locationLat: String, locationLon: String, languageCode: String) {
//        try {
//            val fetchFutureWeatherByCoords = OpenWeatherApiService
//                    .getFutureWeatherAsync("54.6108", "39.7319", languageCode)
//                    .await()
//            _downloadedFutureWeather.postValue(fetchFutureWeatherByCoords)
//            println("123456"+fetchFutureWeatherByCoords)
//
//        } catch (e: NoConnectivityException){
//            Log.e("Connectivity","No internet Connection", e)
//        }
//
//
//
//
//    }

    override suspend fun fetchDailyWeatherByCoord(locationLat: String, locationLon: String, languageCode: String) {
        try{
            val fetchCurrentWeather = OpenWeatherApiService
                    .getFutureWeatherDailyAsync(locationLat,locationLon,languageCode)
                    .await()
            _downloadedDailyWeather.postValue(fetchCurrentWeather)
        }catch (e: NoConnectivityException){
            Log.e("Connectivity","No internet Connection", e)
        }
    }


}