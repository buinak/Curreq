package com.buinak.curreq.data.Local;

import android.graphics.Bitmap;

import com.buinak.curreq.entities.CurreqEntity.BitmapWrapper;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalDataHandler implements LocalDataSource{

    private CurrencyDatabase database;
    private LocalCacheHandler localCacheHandler;

    public LocalDataHandler(CurrencyDatabase database, LocalCacheHandler localCacheHandler) {
        this.database = database;
        this.localCacheHandler = localCacheHandler;
    }

    public Single<RateRequestRecord> getLatestRecord() {
        return Single.just(database.getLatestRealmRecord());
    }

    public Single<List<CurrencyRecord>> getCurrencyList() {
        return Single.just(database.getAllCurrencies());
    }

    @Override
    public Single<Bitmap> getBitmap(String code) {
        return Single.just(localCacheHandler.getBitmap(code));
    }

    @Override
    public Single<List<BitmapWrapper>> getAllBitmaps() {
        return Single.just(localCacheHandler.getBitmaps());
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
    public boolean hasCurrencyRateRecords() {
        return database.hasCurrencyRateRecords();
    }

    @Override
    public boolean hasCurrencyRecords() {
        return database.hasCurrencyRecords();
    }
}
