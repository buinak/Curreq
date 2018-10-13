package com.buinak.curreq.data;

import com.buinak.curreq.data.Local.LocalDataSource;
import com.buinak.curreq.data.Remote.RemoteDataSource;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.SingleSubject;

public class Repository implements DataSource {


    public LocalDataSource localDataSource;
    public RemoteDataSource remoteDataSource;

    private CompositeDisposable disposable;

    public Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;

        disposable = new CompositeDisposable();

        if (localDataSource.hasCurrencyRecords()){
            disposable.add(localDataSource.getCurrencyList()
                    .subscribe(remoteDataSource::setCurrencyList));
        }
    }

    @Override
    public Single<RateRequestRecord> requestRecord() {
        if (localDataSource.hasCurrencyRateRecords()) {
            return localDataSource.getLatestRecord();
        } else {
            return requestNewRecord();
        }
    }

    @Override
    public Single<RateRequestRecord> requestNewRecord() {
            SingleSubject<RateRequestRecord> subject = SingleSubject.create();
            if (remoteDataSource.isReady()) {
                return remoteDataSource.getRates()
                        .doAfterSuccess(result -> localDataSource.saveRecord(result));
            } else {
                disposable.add(initialiseRemoteDataSourceAndGetResult()
                        .subscribe(subject::onSuccess));
            }

            return subject;
    }

    @Override
    public Single<List<CurrencyRecord>> requestCurrencyList() {
        if (localDataSource.hasCurrencyRecords()){
            return localDataSource.getCurrencyList();
        } else {
            return remoteDataSource.getCurrencyList()
                    .doAfterSuccess(result -> localDataSource.saveCurrencies(result));
        }
    }

    @Override
    public Single<Boolean> initialiseRepositoryIfFirstStart() {
        if (localDataSource.hasCurrencyRateRecords()){
            return Single.just(true);
        }

        SingleSubject<Boolean> readySubject = SingleSubject.create();
        disposable.add(remoteDataSource.getCurrencyList()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    localDataSource.saveCurrencies(result);
                    disposable.add(remoteDataSource.getRates()
                            .subscribeOn(Schedulers.io())
                            .subscribe(r -> {
                                localDataSource.saveRecord(r);
                                readySubject.onSuccess(true);
                            }));

                }));

        return readySubject;
    }

    private Single<RateRequestRecord> initialiseRemoteDataSourceAndGetResult(){
        SingleSubject<RateRequestRecord> readySubject = SingleSubject.create();
        disposable.add(remoteDataSource.getCurrencyList()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    localDataSource.saveCurrencies(result);
                    disposable.add(remoteDataSource.getRates()
                            .subscribeOn(Schedulers.io())
                            .subscribe(r -> {
                                localDataSource.saveRecord(r);
                                readySubject.onSuccess(r);
                            }));

                }));

        return readySubject;
    }


    @Override
    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
