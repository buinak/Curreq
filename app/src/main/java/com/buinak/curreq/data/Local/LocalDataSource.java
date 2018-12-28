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

public interface LocalDataSource {
    Single<Request> getLatestRecord();
    Single<List<Currency>> getCurrencyList();
    Single<Boolean> isPasswordCorrect();

    Single<Bitmap> getBitmap(String code);
    Single<List<CountryFlagBitmap>> getAllBitmaps();

    Observable<List<CurrencyExchangeRate>> getAllSavedRecords();

    Completable cacheBitmaps();

    void saveRecord(Request record);
    void saveCurrencies(List<Currency> currencyList);
    void saveRate(Pair<Currency, Currency> pair);
    void savePassword(String password);

    void swapRecord(String recordId);

    void resetAllSavedRecords();

    boolean hasCurrencyRateRecords();
    boolean hasCurrencyRecords();

    Observable<Date> getLatestRecordDate();

    boolean isDailyUpdatesOn();
    void setDailyUpdates(Boolean dailyUpdates);
}
