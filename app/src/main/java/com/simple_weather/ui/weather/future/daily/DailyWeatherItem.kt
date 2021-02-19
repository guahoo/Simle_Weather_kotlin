package com.simple_weather.ui.weather.future.daily

import android.content.Context
import androidx.core.content.ContextCompat
import com.simple_weather.R
import com.simple_weather.data.db.entity.weather_entry.ConditionDailyWeather
import com.simple_weather.internal.*
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_daily_weather.*
import kotlinx.android.synthetic.main.item_daily_weather.imageView_condition_icon
import kotlinx.android.synthetic.main.item_daily_weather.textView_date
import kotlinx.android.synthetic.main.item_future_weather.*
import org.json.JSONArray

class DailyWeatherItem(val weatherEntry: ConditionDailyWeather, val context: Context?) : Item()
{
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            updateFutureTimeDate(weatherEntry.dt)
            //updateFutureTime(weatherEntry.dt)
            updateFutureTemp(weatherEntry.day_temp, weatherEntry.night_temp)
            //updateFutureWeather(weatherEntry.weather)
            updateStatusWeatherView(weatherEntry.weather)
            setImageViewsColor(true)

        }

    }




    override fun getLayout(): Int  = R.layout.item_daily_weather



    private fun ViewHolder.updateFutureWeather(weather:String) {
        textView_condition.text = JSONArray(weather).getJSONObject(0).get("description") as String?
    }

    private fun ViewHolder.updateFutureTemp(day_temp: Double, night_temp: Double){
        tv_dayTemp_value.text = WeatherParamConverter().convertTemp(day_temp)
        tv_nightTemp_value.text = WeatherParamConverter().convertTemp(night_temp)

    }

    private fun ViewHolder.updateFutureTimeDate(dateTime: Long){

        textView_date.text = WeatherParamConverter().convertDateTime(dateTime, MIDDLEDATETIMEPATTERN)
    }

    private fun ViewHolder.updateFutureTime(dt: Long) {
        textView_time.text = WeatherParamConverter().convertDateTime(dt, SHORTTIMEPATTERN)
    }

    private fun ViewHolder.updateStatusWeatherView(weather: String){
        imageView_condition_icon!!.setImageResource(
            WeatherIconMap.getResourceIdent(
            JSONArray(weather).getJSONObject(0).get("main") as String?))
    }

    private fun ViewHolder.setImageViewsColor(night: Boolean) {
        val imageColor = if (night) R.color.white else R.color.black
        imageView_condition_icon.setColorFilter(ContextCompat.getColor(context?.applicationContext!!,imageColor))


    }
}