package com.buinak.curreq.ui.SettingsScreen

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import butterknife.BindView
import butterknife.ButterKnife
import com.buinak.curreq.R
import javax.inject.Inject
import kotlin.jvm.javaClass

class SettingsActivity : AppCompatActivity() {

    lateinit var autoUpdatesCheckBox: CheckBox

    private lateinit var viewModel: SettingsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        autoUpdatesCheckBox = findViewById(R.id.settings_auto_updates_checkbox)

        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)

        viewModel.dailyUpdatesLiveData.observe(this, Observer { b -> if (b != null) autoUpdatesCheckBox.isChecked = b })
        autoUpdatesCheckBox.setOnClickListener { viewModel.dailyUpdatesChanged(autoUpdatesCheckBox.isChecked) }
    }
}
