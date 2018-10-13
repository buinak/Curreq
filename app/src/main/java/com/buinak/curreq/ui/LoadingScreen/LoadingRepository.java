package com.buinak.curreq.ui.LoadingScreen;

import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.List;

import javax.inject.Inject;

public class LoadingRepository implements DataSource.DataSourceListener {

    @Inject
    DataSource dataSource;

    private LoadingViewModel viewModel;

    public LoadingRepository(LoadingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void initialise(){
        dataSource.requestRecord();
    }

    @Override
    public void onRateRequestRecordReceived(RateRequestRecord record) {
        viewModel.setIsReady(true);
    }

    @Override
    public void onCurrencyRecordsReceived(List<CurrencyRecord> records) {

    }
}
