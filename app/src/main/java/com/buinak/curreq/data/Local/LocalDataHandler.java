package com.buinak.curreq.data.Local;

import android.graphics.Bitmap;
import android.util.Pair;

import com.buinak.curreq.entities.CurreqEntity.BitmapWrapper;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;
import com.buinak.curreq.entities.CurreqEntity.SavedRateRecord;

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

    public Single<RateRequestRecord> getLatestRecord() {
        return database.getLatestRealmRecord();
    }

    public Single<List<CurrencyRecord>> getCurrencyList() {
        return database.getAllCurrencies();
    }

    @Override
    public Single<Bitmap> getBitmap(String code) {
        return localCacheHandler.getBitmap(code);
    }

    @Override
    public Single<List<BitmapWrapper>> getAllBitmaps() {
        return localCacheHandler.getBitmaps();
    }

    @Override
    public Observable<List<SavedRateRecord>> getAllSavedRecords() {
        return database.getAllSavedRecords();
    }

    @Override
    public Completable cacheBitmaps() {
        return localCacheHandler.initialiseBitmaps();
    }

    @Override
    public void saveRecord(RateRequestRecord record) {
        database.saveRecord(record);
    }

    @Override
    public void saveCurrencies(List<CurrencyRecord> currencyRecordList) {
        database.saveCurrencies(currencyRecordList);
    }

    @Override
    public void saveRate(Pair<CurrencyRecord, CurrencyRecord> pair) {
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
}
