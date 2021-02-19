package com.simple_weather

import android.app.Application
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import com.simple_weather.Provider.LocationProviderImpl
import com.simple_weather.data.db.ForecastDataBase
import com.simple_weather.data.repository.ForecastRepository
import com.simple_weather.data.repository.ForecastRepositoryImpl
//import com.simple_weather.internal.PreferenceHelper

import com.simple_weather.network.*
import com.simple_weather.ui.weather.current.CurrentWeatherViewModelFactory
import com.simple_weather.ui.weather.future.daily.DailyListWeatherViewModelFactory
import com.simple_weather.ui.weather.future.detail.FutureDetailWeatherViewModelFactory
import com.simple_weather.ui.weather.future.list.FutureListWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class ForecastApplication: Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDataBase(instance()) }
        bind() from singleton { instance<ForecastDataBase>().currentWeatherDao()}
        bind() from singleton { instance<ForecastDataBase>().futureWeatherDao()}
        bind() from singleton { instance<ForecastDataBase>().dailyWeatherDao()}
        bind<ConnectivityInterseptor>() with singleton { ConnectivityInterseptorImpl(instance()) }
        bind() from singleton { OpenWeatherApiService(instance()) }
        bind<WeatherDataSourse>() with singleton { WeatherDataSourseImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance(),instance(),instance(),instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProviderImpl>() with singleton { LocationProviderImpl(instance(),instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance()) }
        bind() from provider { DailyListWeatherViewModelFactory(instance()) }
        bind() from factory { detailDate: Long -> FutureDetailWeatherViewModelFactory(instance(),detailDate) }

    }



    override fun onCreate() {

        super.onCreate()
        AndroidThreeTen.init(this)



    }
}