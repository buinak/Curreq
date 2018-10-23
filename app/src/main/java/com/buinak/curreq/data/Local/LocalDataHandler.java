package com.buinak.curreq.data.Local;

import android.graphics.Bitmap;
import android.util.Pair;

import com.buinak.curreq.entities.CurreqEntity.CountryFlagBitmap;
import com.buinak.curreq.entities.CurreqEntity.Currency;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRate;
import com.buinak.curreq.entities.CurreqEntity.Request;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class LocalDataHandler implements LocalDataSource{

    private CurrencyDatabase database;
    private LocalCacheHandler localCacheHandler;

    public LocalDataHandler(CurrencyDatabase database, LocalCacheHandler localCacheHandler) {
        this.database = database;
        this.localCacheHandler = localCacheHandler;
    }

    public Single<Request> getLatestRecord() {
        return database.getLatestRealmRecord();
    }

    public Single<List<Currency>> getCurrencyList() {
        return database.getAllCurrencies();
    }

    @Override
    public Single<Bitmap> getBitmap(String code) {
        return localCacheHandler.getBitmap(code);
    }

    @Override
    public Single<List<CountryFlagBitmap>> getAllBitmaps() {
        return localCacheHandler.getBitmaps();
    }

    @Override
    public Observable<List<CurrencyExchangeRate>> getAllSavedRecords() {
        return database.getAllSavedRecords();
    }

    @Override
    public Completable cacheBitmaps() {
        return localCacheHandler.initialiseBitmaps();
    }

    @Override
    public void saveRecord(Request record) {
        database.saveRecord(record);
    }

    @Override
    public void saveCurrencies(List<Currency> currencyList) {
        database.saveCurrencies(currencyList);
    }

    @Override
    public void saveRate(Pair<Currency, Currency> pair) {
        database.saveRate(pair);
    }

    @Override
    public void swapRecord(String recordId) {
        database.swapRecord(recordId);
    }

    @Override
    public void resetAllSavedRecords() {
        database.resetAllSavedRecords();
    }

    @Override
    public boolean hasCurrencyRateRecords() {
        return database.hasCurrencyRateRecords();
    }

    @Override
    public boolean hasCurrencyRecords() {
        return database.hasCurrencyRecords();
    }

    @Override
    public Observable<Date> getLatestRecordDate() {
        return database.getLatestRecordDate();
    }
}
