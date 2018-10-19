package com.buinak.curreq.data.Local;

import android.graphics.Bitmap;
import android.util.Pair;

import com.buinak.curreq.entities.CurreqEntity.BitmapWrapper;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;
import com.buinak.curreq.entities.RealmEntity.RealmSavedRateRecord;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface LocalDataSource {
    Single<RateRequestRecord> getLatestRecord();
    Single<List<CurrencyRecord>> getCurrencyList();

    Single<Bitmap> getBitmap(String code);
    Single<List<BitmapWrapper>> getAllBitmaps();

    Flowable<List<RealmSavedRateRecord>> getAllSavedRecords();

    Completable cacheBitmaps();

    void saveRecord(RateRequestRecord record);
    void saveCurrencies(List<CurrencyRecord> currencyRecordList);
    void saveRate(Pair<CurrencyRecord, CurrencyRecord> pair);

    boolean hasCurrencyRateRecords();
    boolean hasCurrencyRecords();
}
