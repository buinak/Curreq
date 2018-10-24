package com.buinak.curreq.ui.SettingsScreen

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.buinak.curreq.application.CurreqApplication
import javax.inject.Inject

class SettingsViewModel : ViewModel() {
    @Inject
    lateinit var repository: SettingsRepository

    val dailyUpdatesLiveData: LiveData<Boolean>

    init {
        CurreqApplication.inject(this)
        dailyUpdatesLiveData = MutableLiveData()
        dailyUpdatesLiveData.value = repository.isDailyUpdatesOn()
    }

    fun dailyUpdatesChanged(boolean: Boolean) = repository.setDailyUpdates(boolean)
}