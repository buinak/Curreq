package com.buinak.curreq.Data;

import com.buinak.curreq.Data.Local.CurrencyDatabase;
import com.buinak.curreq.Data.Local.LocalDataSource;
import com.buinak.curreq.Data.Remote.CurrencyRepository;
import com.buinak.curreq.Data.Remote.RemoteDataSource;
import com.buinak.curreq.Entities.CurreqEntity.RateRequestRecord;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Repository implements DataSource {


    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    private DataSourceListener listener;

    private Disposable openRequest;

    public Repository(DataSourceListener listener) {
        this.listener = listener;
        localDataSource = CurrencyDatabase.getInstance();
        remoteDataSource = CurrencyRepository.getInstance();
    }

    public static DataSource getNewInstance(DataSourceListener listener){
        return new Repository(listener);
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
}
