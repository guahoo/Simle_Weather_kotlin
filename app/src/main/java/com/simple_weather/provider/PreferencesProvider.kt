package com.simple_weather.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

abstract class PreferencesProvider(context: Context) {
    val context = context.applicationContext
    val preferences:SharedPreferences get() = PreferenceManager.getDefaultSharedPreferences(context)

}