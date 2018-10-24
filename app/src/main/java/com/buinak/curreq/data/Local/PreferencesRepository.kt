package com.buinak.curreq.data.Local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.buinak.curreq.utils.Constants

class PreferencesRepository(val context: Context) {

    val settingsPreferences: SharedPreferences = context.getSharedPreferences(Constants.SETTINGS_PREFERENCES, MODE_PRIVATE)

    fun isDailyUpdatesOn(): Boolean {
        val boolean: Boolean = settingsPreferences.getBoolean("DAILY_UPDATES", false)
        return boolean
    }


    fun setDailyUpdates(boolean: Boolean) {
        settingsPreferences.edit().putBoolean("DAILY_UPDATES", boolean).apply()
    }

}