package com.buinak.curreq.data;

import android.graphics.Bitmap;

import com.buinak.curreq.data.Local.LocalDataSource;
import com.buinak.curreq.data.Remote.RemoteDataSource;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;
import com.buinak.curreq.utils.RepositoryUtils;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;
import io.reactivex.subjects.SingleSubject;

public class Repository implements DataSource {


    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    private CompositeDisposable disposable;

    public Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;

        disposable = new CompositeDisposable();

        if (localDataSource.hasCurrencyRecords()) {
            disposable.add(localDataSource.getCurrencyList()
                    .subscribeOn(Schedulers.io())
                    .subscribe(remoteDataSource::setCurrencyList));
        }
    }

    @Override
    public Single<RateRequestRecord> requestRecord() {
        if (localDataSource.hasCurrencyRateRecords()) {
            return localDataSource.getLatestRecord()
                    .subscribeOn(Schedulers.io());
        } else {
            return requestNewRecord();
        }
    }

    @Override
    public Single<RateRequestRecord> requestNewRecord() {
        SingleSubject<RateRequestRecord> subject = SingleSubject.create();
        if (remoteDataSource.isReady()) {
            return remoteDataSource.getRates()
                    .subscribeOn(Schedulers.io())
                    .doAfterSuccess(result -> localDataSource.saveRecord(result));
        } else {
            disposable.add(initialiseRemoteDataSourceAndGetResult()
                    .subscribeOn(Schedulers.io())
                    .subscribe(subject::onSuccess));
        }

        return subject;
    }

    @Override
    public Single<List<CurrencyRecord>> requestFullCurrencyList() {
        if (localDataSource.hasCurrencyRecords()) {
            return localDataSource.getCurrencyList()
                    .subscribeOn(Schedulers.io());
        } else {
            return remoteDataSource.getCurrencyList()
                    .subscribeOn(Schedulers.io())
                    .doAfterSuccess(result -> localDataSource.saveCurrencies(result));
        }
    }

    @Override
    public Single<List<CurrencyRecord>> requestFilteredCurrencyList() {
        SingleSubject<List<CurrencyRecord>> subject = SingleSubject.create();

        if (localDataSource.hasCurrencyRecords()) {
            disposable.add(localDataSource.getCurrencyList()
                    .subscribeOn(Schedulers.io())
                    .map(RepositoryUtils::filterList)
                    .subscribe(subject::onSuccess));
        } else {
            disposable.add(remoteDataSource.getCurrencyList()
                    .subscribeOn(Schedulers.io())
                    .map(RepositoryUtils::filterList)
                    .subscribe(result -> {
                                localDataSource.saveCurrencies(result);
                                subject.onSuccess(result);
                            }
                    ));
        }

        return subject;
    }

    @Override
    public Completable initialiseRepositoryIfFirstStart() {
        if (localDataSource.hasCurrencyRateRecords()) {
            return Completable.complete();
        }

        CompletableSubject completable = CompletableSubject.create();
        disposable.add(remoteDataSource.getCurrencyList()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    localDataSource.saveCurrencies(result);
                    disposable.add(remoteDataSource.getRates()
                            .subscribeOn(Schedulers.io())
                            .subscribe(r -> {
                                localDataSource.saveRecord(r);
                                completable.onComplete();
                            }));

                }));

        return completable;
    }

    @Override
    public Completable initialiseBitmaps() {
        return localDataSource.cacheBitmaps();
    }

    @Override
    public Single<Bitmap> getBitmap(String code) {
        return localDataSource.getBitmap(code);
    }

    private Single<RateRequestRecord> initialiseRemoteDataSourceAndGetResult() {
        SingleSubject<RateRequestRecord> readySubject = SingleSubject.create();
        disposable.add(remoteDataSource.getCurrencyList()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    localDataSource.saveCurrencies(result);
                    disposable.add(remoteDataSource.getRates()
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
