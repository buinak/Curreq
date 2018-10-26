package com.buinak.curreq.ui.SettingsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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