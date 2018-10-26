package com.buinak.curreq.ui.SettingsScreen

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
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
