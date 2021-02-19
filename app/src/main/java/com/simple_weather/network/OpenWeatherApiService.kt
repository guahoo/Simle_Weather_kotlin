package com.simple_weather.network


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.simple_weather.network.networkresponse.WeatherAllInOneResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

const val API_KEY = "b542736e613d2382837ad821803eb507"


interface OpenWeatherApiService {


    @GET("onecall")
    fun getFutureWeatherDailyAsync(
            @Query("lat") latitude: String = "54.6108",
            @Query("lon") longitude: String = "39.7319",
            @Query("lang") languageCode: String = Locale.getDefault().country,
            @Query("units") units: String = "metric")
            : Deferred<WeatherAllInOneResponse>


    companion object {
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