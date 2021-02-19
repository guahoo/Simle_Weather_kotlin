package com.simple_weather.ui.weather.future.list

import android.graphics.PorterDuff
import com.simple_weather.R
import com.simple_weather.data.db.entity.weather_entry.ConditionFutureWeatherEntry
import com.simple_weather.internal.*
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_future_weather.*
import org.json.JSONArray

class FutureWeatherItem(val weatherEntry: ConditionFutureWeatherEntry) : Item()
{
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            updateFutureTimeDate(weatherEntry.dt)
            updateFutureTime(weatherEntry.dt)
            updateFutureTemp(weatherEntry.main_temp)
            updateFutureWeather(weatherEntry.weather)
            updateStatusWeatherView(weatherEntry.weather)
          //  setImageViewsColor(false)

        }

    }




    override fun getLayout(): Int  = R.layout.item_future_weather



    private fun ViewHolder.updateFutureWeather(weather:String) {
        textView_condition.text = JSONArray(weather).getJSONObject(0).get("description") as String?
    }

    private fun ViewHolder.updateFutureTemp(currentTemp: Double){
        textView_temperature.text = WeatherParamConverter().convertTemp(currentTemp)

    }

    private fun ViewHolder.updateFutureTimeDate(dateTime: Long){

        textView_date.text = WeatherParamConverter().convertDateTime(dateTime, SHORDATEPATTERN)
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
        imageView_condition_icon.setColorFilter(imageColor,PorterDuff.Mode.DST_ATOP)


    }
}