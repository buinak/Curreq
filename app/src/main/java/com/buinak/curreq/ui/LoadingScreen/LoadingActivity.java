package com.buinak.curreq.ui.LoadingScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.buinak.curreq.data.DaggerRepositoryComponent;
import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.data.Remote.Fixer.FixerIOApi;
import com.buinak.curreq.data.RepositoryComponent;
import com.buinak.curreq.data.RepositoryModule;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;
import com.buinak.curreq.R;

import javax.inject.Inject;


public class LoadingActivity extends AppCompatActivity implements DataSource.DataSourceListener {

    @Inject
    DataSource source;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        RepositoryComponent component = DaggerRepositoryComponent.builder().build();
        component.inject(this);

        source.setListener(this);
        source.requestRecord();
    }

    @Override
    public void onRateRequestRecordReceived(RateRequestRecord record) {
        System.out.println();
        System.out.println();
    }
}
