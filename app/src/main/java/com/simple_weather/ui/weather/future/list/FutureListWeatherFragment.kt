package com.simple_weather.ui.weather.future.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.simple_weather.R
import com.simple_weather.data.db.entity.weather_entry.ConditionFutureWeatherEntry
import com.simple_weather.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class FutureListWeatherFragment : ScopedFragment(),KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: FutureListWeatherViewModelFactory by instance()


    private lateinit var viewModel: FutureListWeatherViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FutureListWeatherViewModel::class.java)
        bindUI()
        // TODO: Use the ViewModel
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeatherEntries = viewModel.weatherEntries.await()

        futureWeatherEntries.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            group_loading.visibility = View.GONE
            initRecyclerView(it.toFutureWeatherItems())


        })
    }

    private fun List<ConditionFutureWeatherEntry>.toFutureWeatherItems() : List<FutureWeatherItem> {
        return this.map {
            FutureWeatherItem(it)
        }
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as? FutureWeatherItem)?.let {
              showDetailWeather(it.weatherEntry.dt,view)

            }
        }
    }

    private fun showDetailWeather(date: Long,view: View){

        val actionDetail = FutureListWeatherFragmentDirections.actionDetail(date)
        Navigation.findNavController(view).navigate(actionDetail)


    }
}