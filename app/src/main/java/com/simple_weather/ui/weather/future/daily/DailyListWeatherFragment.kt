package com.simple_weather.ui.weather.future.daily

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.simple_weather.R
import com.simple_weather.data.db.entity.weather_entry.ConditionDailyWeather
import com.simple_weather.ui.base.ScopedFragment
import com.simple_weather.ui.weather.current.GEOCODER_LOCALITY_NAME
import com.simple_weather.ui.weather.future.detail.FutureDetailWeatherFragmentArgs
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.android.synthetic.main.item_daily_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.Multi2
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.kodein.di.generic.factory2
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


class DailyListWeatherFragment : ScopedFragment(),KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory  : ((String,Long) -> DailyListWeatherViewModelFactory) by factory2()


    private lateinit var viewModel: DailyListWeatherViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.daily_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val safeArgs = arguments?.let { FutureDetailWeatherFragmentArgs.fromBundle(it) }
        val preferences:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val datetime: LocalDateTime = LocalDateTime.now()
        val milliseconds = datetime.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()?.div(1000)

        
        viewModel = ViewModelProvider(this, viewModelFactory(preferences.getString(GEOCODER_LOCALITY_NAME, "London"),milliseconds!!)).get(DailyListWeatherViewModel::class.java)
        bindUI()
        // TODO: Use the ViewModel
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val dailyWeatherEntries = viewModel.weatherEntries.await()

        dailyWeatherEntries.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            group_loading.visibility = View.GONE
            initRecyclerView(it.toFutureWeatherItems())
            //setImageViewsColor(true)


        })
    }

    private fun List<ConditionDailyWeather>.toFutureWeatherItems() : List<DailyWeatherItem> {
        return this.map {
            DailyWeatherItem(it, context?.applicationContext)
        }
    }

    private fun initRecyclerView(items: List<DailyWeatherItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@DailyListWeatherFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as? DailyWeatherItem)?.let {
             // showDetailWeather(it.weatherEntry.dt,view)

            }
        }
    }

//    private fun setImageViewsColor(night: Boolean) {
//        val imageColor = if (night) R.color.white else R.color.black
//        imageView_condition_icon.setColorFilter(ContextCompat.getColor(context?.applicationContext!!, imageColor))
//
//
//    }

//    private fun showDetailWeather(date: Long,view: View){
//
//        val actionDetail = FutureListWeatherFragmentDirections.actionDetail(date)
//        Navigation.findNavController(view).navigate(actionDetail)
//
//
//    }
}




