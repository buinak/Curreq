package com.buinak.curreq.ui.SettingsScreen

import com.buinak.curreq.data.DataSource
import io.reactivex.disposables.CompositeDisposable

class SettingsRepository (private val dataSource: DataSource) {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun test(){
        println()
        println()
    }
}