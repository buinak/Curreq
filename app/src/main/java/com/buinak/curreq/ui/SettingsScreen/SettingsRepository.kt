package com.buinak.curreq.ui.SettingsScreen

import com.buinak.curreq.data.DataSource
import io.reactivex.disposables.CompositeDisposable

class SettingsRepository (private val dataSource: DataSource) {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        val isDailyUpdatesOn: Boolean = dataSource.isDailyUpdatesOn
    }

    fun setDailyUpdates(boolean: Boolean) = dataSource.setDailyUpdates(boolean)

    fun isDailyUpdatesOn(): Boolean = dataSource.isDailyUpdatesOn
    fun setPassword(password: String) {
        dataSource.saveDebugPassword(password).subscribe()
    }
}