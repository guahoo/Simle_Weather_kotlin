package com.simple_weather.ui.settings

import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.preference.*
import com.simple_weather.R
import com.simple_weather.network.SearchLocationNameTask
import org.json.JSONArray
import org.json.JSONException


private lateinit var body: TextView
private lateinit var bar: ListView
private lateinit var yesBtn: ImageButton
private lateinit var loader: ProgressBar
private const val CITY_NAME_DISPLAY_LIST = "name"
private const val DISPLAYED_NAME = "display_name"
private lateinit var dialog: Dialog

class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.peresences)

        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)


        val locationName = preferenceScreen.getPreference(2)
        locationName!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            showDialog()
            false
        }

    }


    private fun showDialog() {

        dialog = Dialog(this.context)



        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setCancelable(false)
        dialog.setContentView(R.layout.city_name_input_dialog)
        body = dialog.findViewById(R.id.cityNameEditText) as TextView
        yesBtn = dialog.findViewById(R.id.ok_Btn) as ImageButton
        bar = dialog.findViewById(R.id.cityList) as ListView
        loader = dialog.findViewById(R.id.loader) as ProgressBar
        body.addTextChangedListener(watcher)
        loader.visibility=View.GONE
        bar.visibility = View.INVISIBLE

        dialog.setOnDismissListener(DialogInterface.OnDismissListener { })




        yesBtn.setOnClickListener {
            if (body.text.toString().isNotEmpty()) saveCityName(body.text.toString())
            dialog.hide()
        }

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT


        // yesBtn.setOnClickListener { dialog.dismiss() }

        dialog.show()
        dialog.window!!.attributes = lp

    }//closes the switch

    private fun saveCityName(toString: String) {
        val context = context?.applicationContext
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        preferences.edit().putString("CUSTOM_LOCATION", toString).apply()


    }


    override fun onResume() {
        super.onResume()
        for (i in 2 until preferenceScreen.preferenceCount) {
            val preference: Preference = preferenceScreen.getPreference(i)
            if (preference is PreferenceGroup) {
                val preferenceGroup: PreferenceGroup = preference
                for (j in 2 until preferenceGroup.preferenceCount) {
                    val singlePref: Preference = preferenceGroup.getPreference(j)
                    updatePreference(singlePref, singlePref.key)
                }
            } else {
                updatePreference(preference, preference.key)
            }
        }
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        updatePreference(findPreference(key), key)
    }

    private fun updatePreference(preference: Preference?, key: String) {
        if (preference == null) return

        if (preference is ListPreference) {
            val listPreference: ListPreference = preference
            listPreference.title = listPreference.entry
            return
        }
        try {
            val sharedPrefs: SharedPreferences = preferenceManager.sharedPreferences
            preference.title = "Выбран город: ${sharedPrefs.getString(key, "London")}"
        } catch (e: ClassCastException) {
            Log.e("ClassCastException", e.toString())
        }

    }


    private val watcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            yesBtn.visibility = View.GONE


            if(bar.visibility == View.VISIBLE || yesBtn.visibility == View.VISIBLE){
                loader.visibility = View.GONE
            } else{
                loader.visibility = View.VISIBLE
            }





            val searchText = s.toString().trim()

            if(searchText.length>0){
                getLocationsNames(searchText)
                val adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, getLocationsNames(body.text.toString()))
                bar.adapter = adapter
                bar.visibility = View.VISIBLE
                loader.visibility = View.GONE
            } else{
                bar.visibility = View.GONE
                yesBtn.visibility = View.VISIBLE
            }



            bar.setOnItemClickListener { _, _, position, _ ->
                body.text = getLocationsNames(body.text.toString())[position]
                yesBtn.visibility = View.VISIBLE
                bar.visibility = View.GONE
            }


        }

        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    }

    private fun getLocationsNames(location: String): ArrayList<String?> {
        val cityNames = ArrayList<String?>()

        val searchLocationNameTask = SearchLocationNameTask(location)


        try {
            val geoData = JSONArray(searchLocationNameTask.execute(location).get())
            for (i in 0 until geoData.length()) {
                val placeInfo = geoData.getJSONObject(i)
                cityNames.add(placeInfo.getString(DISPLAYED_NAME))
            }
        } catch (jE:JSONException){
            body.text = "So we got exception("
        }


        return cityNames

    }

}