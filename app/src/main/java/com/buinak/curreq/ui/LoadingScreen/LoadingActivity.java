package com.buinak.curreq.ui.LoadingScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.buinak.curreq.R;
import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import javax.inject.Inject;


public class LoadingActivity extends AppCompatActivity implements DataSource.DataSourceListener {

    @Inject
    DataSource source;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        CurreqApplication.getRepositoryComponent(this).inject(this);

        source.requestNewRecord();
    }

    @Override
    public void onRateRequestRecordReceived(RateRequestRecord record) {
        System.out.println();
        System.out.println();
    }
}
