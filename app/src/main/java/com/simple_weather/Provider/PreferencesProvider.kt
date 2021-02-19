package com.simple_weather.Provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

abstract class PreferencesProvider(context: Context) {
    val context = context.applicationContext
    val preferences:SharedPreferences get() = PreferenceManager.getDefaultSharedPreferences(context)

}