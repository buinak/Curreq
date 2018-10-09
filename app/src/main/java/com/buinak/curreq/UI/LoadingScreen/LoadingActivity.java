package com.buinak.curreq.UI.LoadingScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.buinak.curreq.Data.DataSource;
import com.buinak.curreq.Data.Repository;
import com.buinak.curreq.Entities.CurreqEntity.RateRequestRecord;
import com.buinak.curreq.R;


public class LoadingActivity extends AppCompatActivity implements DataSource.DataSourceListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        DataSource source = Repository.getNewInstance(this);
        source.requestRecord();
    }

    @Override
    public void onRateRequestRecordReceived(RateRequestRecord record) {
        System.out.println();
        System.out.println();
    }
}
