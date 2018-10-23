package com.buinak.curreq.data;

import android.graphics.Bitmap;
import android.util.Pair;

import com.buinak.curreq.data.Local.LocalDataSource;
import com.buinak.curreq.data.Remote.RemoteDataSource;
import com.buinak.curreq.entities.CurreqEntity.CountryFlagBitmap;
import com.buinak.curreq.entities.CurreqEntity.Currency;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRate;
import com.buinak.curreq.entities.CurreqEntity.Request;
import com.buinak.curreq.utils.RepositoryUtils;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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
    public Single<Request> requestRecord() {
        if (localDataSource.hasCurrencyRateRecords()) {
            return localDataSource.getLatestRecord()
                    .subscribeOn(Schedulers.io());
        } else {
            return requestNewRecord();
        }
    }

    @Override
    public Single<Request> requestNewRecord() {
        if (remoteDataSource.isReady()) {
            return remoteDataSource.getRates()
                    .subscribeOn(Schedulers.io())
                    .doAfterSuccess(result -> localDataSource.saveRecord(result));
        } else {
            return initialiseRemoteDataSourceAndGetResult()
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Completable updateRecords() {
        return Completable.fromSingle(requestNewRecord());
    }

    @Override
    public Single<List<Currency>> requestFullCurrencyList() {
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
    public Single<List<Currency>> requestFilteredCurrencyList() {

        if (localDataSource.hasCurrencyRecords()) {
            return localDataSource.getCurrencyList()
                    .subscribeOn(Schedulers.io())
                    .map(RepositoryUtils::filterList);
        } else {
            return remoteDataSource.getCurrencyList()
                    .subscribeOn(Schedulers.io())
                    .map(RepositoryUtils::filterList)
                    .doOnSuccess(result -> localDataSource.saveCurrencies(result));
        }
    }

    @Override
    public Completable saveRatePair(Pair<Currency, Currency> pair) {
        return Completable.fromAction(() -> localDataSource.saveRate(pair));
    }

    @Override
    public Observable<List<CurrencyExchangeRate>> getAllSavedRecords() {
        return localDataSource.getAllSavedRecords();
    }

    @Override
    public void swapRecord(String recordId) {
        localDataSource.swapRecord(recordId);
    }

    @Override
    public void resetAllSavedRecords() {
        localDataSource.resetAllSavedRecords();
    }

    @Override
    public Completable initialiseRepositoryIfFirstStart() {
        if (localDataSource.hasCurrencyRateRecords()) {
            return Completable.complete();
        }

        return Completable.fromSingle(remoteDataSource.getCurrencyList()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(result -> localDataSource.saveCurrencies(result))
                .flatMap(result -> remoteDataSource.getRates())
                .doOnSuccess(rateRecord -> localDataSource.saveRecord(rateRecord)));
    }

    @Override
    public Completable initialiseBitmaps() {
        return localDataSource.cacheBitmaps();
    }

    @Override
    public Single<Bitmap> getBitmap(String code) {
        return localDataSource.getBitmap(code);
    }

    @Override
    public Single<List<CountryFlagBitmap>> getAllBitmaps() {
        return localDataSource.getAllBitmaps();
    }

    private Single<Request> initialiseRemoteDataSourceAndGetResult() {
        return remoteDataSource.getCurrencyList()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(currencyRecords -> localDataSource.saveCurrencies(currencyRecords))
                .flatMap(r -> remoteDataSource.getRates())
                .doOnSuccess(result -> localDataSource.saveRecord(result));
    }


    @Override
    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
