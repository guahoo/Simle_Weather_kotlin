package com.simple_weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.simple_weather.data.db.entity.future.futureDaily.ConditionDailyWeather
import com.simple_weather.data.db.entity.future.futureDaily.FutureDailyWeatherEntry

@Dao
    interface FutureDailyDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(futureDailyEntries: List<FutureDailyWeatherEntry>)

        @Query("select * from daily_weather")
        fun getDailyWeather() : LiveData<List<ConditionDailyWeather>>

//        @Query("select * from daily_weather where dt = :date")
//        fun getDetailedFutureWeather(date:Long) : LiveData<ConditionDailyWeather>

}