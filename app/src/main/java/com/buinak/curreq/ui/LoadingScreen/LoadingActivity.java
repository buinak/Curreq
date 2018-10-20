package com.buinak.curreq.ui.LoadingScreen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.ActivityState;
import com.buinak.curreq.ui.MainScreen.MainActivity;


public class LoadingActivity extends AppCompatActivity{

    private LoadingViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        viewModel = ViewModelProviders.of(this).get(LoadingViewModel.class);

        viewModel.getIsReady().observe(this, ready -> {
            if (ready != null) {
                if (ready) {
                    startMainActivity();
                }
            }
        });

        viewModel.getActivityState().observe(this, activityState -> {
            if (activityState == ActivityState.FINISHED){
                finish();
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
