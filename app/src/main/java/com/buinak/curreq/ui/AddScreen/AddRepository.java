package com.buinak.curreq.ui.AddScreen;

import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.List;

import javax.inject.Inject;

public class AddRepository implements DataSource.DataSourceListener {

    @Inject
    DataSource dataSource;

    private AddViewModel viewModel;

    public AddRepository(AddViewModel viewModel) {
        this.viewModel = viewModel;

        CurreqApplication.getRepositoryComponent(this).inject(this);
        dataSource.requestCurrencyList();
        dataSource.requestRecord();
    }

    @Override
    public void onRateRequestRecordReceived(RateRequestRecord record) {
        System.out.println();
    }

    @Override
    public void onCurrencyRecordsReceived(List<CurrencyRecord> records) {
        System.out.println();
    }
}
