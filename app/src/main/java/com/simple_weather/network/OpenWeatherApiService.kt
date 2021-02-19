package com.simple_weather.network


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.simple_weather.data.db.entity.current.CurrentWeatherEntry
import com.simple_weather.network.networkresponse.futureWeatherResponse.DailyResponse
import com.simple_weather.network.networkresponse.futureWeatherResponse.FutureWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

const val API_KEY = "b542736e613d2382837ad821803eb507"
//http://api.weatherstack.com/current?access_key=37b7d161a7ba600f71daac8e07e436dd&query=New%20York

interface OpenWeatherApiService {

    @GET("weather")
    fun getCurrentWeatherAsync(
        @Query("q") location: String,
        @Query("lang") languageCode: String = Locale.getDefault().country,
        @Query("units") units: String = "metric")
    : Deferred<CurrentWeatherEntry>


    @GET("onecall")
    fun getFutureWeatherAsync(
            @Query("lat") latitude: String = "54.6108",
            @Query("lon") longitude: String = "39.7319",
            @Query("lang") languageCode: String = Locale.getDefault().country,
            @Query("units") units: String = "metric")
    : Deferred<FutureWeatherResponse>

    @GET("onecall")
    fun getFutureWeatherDailyAsync(
            @Query("lat") latitude: String = "54.6108",
            @Query("lon") longitude: String = "39.7319",
            @Query("lang") languageCode: String = Locale.getDefault().country,
            @Query("units") units: String = "metric")
    : Deferred<DailyResponse>


    @GET("weather")
    fun getCurrentWeatherAsyncByCoordsAsync(
            @Query("lat") latitude: String,
            @Query("lon") longitude: String,
            @Query("lang") languageCode: String = Locale.getDefault().country,
            @Query("units") units: String = "metric")
            : Deferred<CurrentWeatherEntry>


    companion object{
        operator fun invoke(connectivityInterseptor: ConnectivityInterseptor
        ): OpenWeatherApiService {

            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterseptor)
                .build()


                return Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl("https://api.openweathermap.org/data/2.5/")
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(OpenWeatherApiService::class.java)


            }


    }


}