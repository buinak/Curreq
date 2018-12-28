package com.buinak.curreq.data.Local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.buinak.curreq.utils.Constants
import com.buinak.curreq.utils.HashUtils.sha256
import io.reactivex.Completable
import io.reactivex.Single
import java.security.MessageDigest

class PreferencesRepository(val context: Context) {

    val settingsPreferences: SharedPreferences = context.getSharedPreferences(Constants.SETTINGS_PREFERENCES, MODE_PRIVATE)

    fun isDailyUpdatesOn(): Boolean {
        val boolean: Boolean = settingsPreferences.getBoolean("DAILY_UPDATES", false)
        return boolean
    }


    fun setDailyUpdates(boolean: Boolean) {
        settingsPreferences.edit().putBoolean("DAILY_UPDATES", boolean).apply()
    }

    fun saveDebugPassword(password: String) {
        val hash = sha256(password)
        settingsPreferences.edit().putString("PASSWORD_HASH", hash).apply()
    }

    fun isDebugPasswordCorrect(): Single<Boolean> {
        val hash = settingsPreferences.getString("PASSWORD_HASH", "")
        val original = "FEFB909F70693909716694528926B63006343F65A80468336875C1FE4CA277CF"
        return Single.just(hash == original)
    }
}