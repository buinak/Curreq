package com.buinak.curreq.data;

import com.buinak.curreq.data.Local.LocalDataSource;
import com.buinak.curreq.data.Remote.RemoteDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Repository implements DataSource {


    public LocalDataSource localDataSource;
    public RemoteDataSource remoteDataSource;

    DataSourceListener listener;

    private Disposable openRequest;

    public Repository(DataSourceListener listener, LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.listener = listener;
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
