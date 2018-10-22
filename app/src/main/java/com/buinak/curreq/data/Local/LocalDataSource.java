package com.buinak.curreq.data.Local;

import android.graphics.Bitmap;
import android.util.Pair;

import com.buinak.curreq.entities.CurreqEntity.CountryFlagBitmap;
import com.buinak.curreq.entities.CurreqEntity.Currency;
import com.buinak.curreq.entities.CurreqEntity.Request;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRateWithId;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface LocalDataSource {
    Single<Request> getLatestRecord();
    Single<List<Currency>> getCurrencyList();

    Single<Bitmap> getBitmap(String code);
    Single<List<CountryFlagBitmap>> getAllBitmaps();

    Observable<List<CurrencyExchangeRateWithId>> getAllSavedRecords();

    Completable cacheBitmaps();

    void saveRecord(Request record);
    void saveCurrencies(List<Currency> currencyList);
    void saveRate(Pair<Currency, Currency> pair);

    void swapRecord(String recordId);

    void resetAllSavedRecords();

    boolean hasCurrencyRateRecords();
    boolean hasCurrencyRecords();
}
