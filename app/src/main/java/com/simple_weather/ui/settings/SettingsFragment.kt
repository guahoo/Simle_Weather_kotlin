package com.simple_weather.ui.settings

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceGroup
import com.simple_weather.R
import java.lang.ClassCastException


class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.peresences)
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
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
            preference.title = "Выбран город: ${sharedPrefs.getString(key, "Default")}"
        } catch (e: ClassCastException){
            Log.e("ClassCastException",e.toString())
        }

    }
}