package com.buinak.curreq.ui.SettingsScreen

import android.arch.lifecycle.ViewModel
import com.buinak.curreq.application.CurreqApplication
import javax.inject.Inject

class SettingsViewModel : ViewModel() {
    @Inject
    lateinit var repository: SettingsRepository

    init {
        CurreqApplication.inject(this)
    }
}