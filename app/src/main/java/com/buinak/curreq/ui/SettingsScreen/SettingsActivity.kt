package com.buinak.curreq.ui.SettingsScreen

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.buinak.curreq.R
import javax.inject.Inject
import kotlin.jvm.javaClass

class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
    }
}
