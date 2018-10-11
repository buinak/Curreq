package com.buinak.curreq.ui.LoadingScreen;

import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import javax.inject.Inject;

public class LoadingRepository implements DataSource.DataSourceListener {

    @Inject
    DataSource dataSource;

    private LoadingViewModel viewModel;

    public LoadingRepository(LoadingViewModel viewModel) {
        this.viewModel = viewModel;
        CurreqApplication.getRepositoryComponent(this).inject(this);
        dataSource.requestRecord();
    }

    @Override
    public void onRateRequestRecordReceived(RateRequestRecord record) {
        viewModel.setIsReady(true);
    }
}
