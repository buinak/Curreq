package com.buinak.curreq.data;

import com.buinak.curreq.data.Local.CurrencyDatabase;
import com.buinak.curreq.data.Local.LocalDataSource;
import com.buinak.curreq.data.Remote.CurrencyRepository;
import com.buinak.curreq.data.Remote.RemoteDataSource;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Repository implements DataSource {


    @Inject
    public LocalDataSource localDataSource;

    @Inject
    public RemoteDataSource remoteDataSource;

    @Inject
    DataSourceListener listener;

    private Disposable openRequest;

    @Inject
    public Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void requestRecord() {
        if (localDataSource.hasRecords()) {
            openRequest = localDataSource.getLatestRecord()
                    .subscribeOn(Schedulers.io())
                    .subscribe(result -> listener.onRateRequestRecordReceived(result));
        } else {
            requestNewRecord();
        }
    }

    @Override
    public void requestNewRecord() {
        openRequest = remoteDataSource.getRates()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    localDataSource.saveRecord(result);
                    listener.onRateRequestRecordReceived(result);
                });

    }

    @Override
    public void dispose() {
        if (openRequest != null) {
            openRequest.dispose();
            openRequest = null;
        }
    }

    @Override
    public void setListener(DataSourceListener listener) {
        this.listener = listener;
    }
}
