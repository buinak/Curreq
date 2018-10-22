package com.buinak.curreq.data;

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

public interface DataSource {

    Single<Request> requestRecord();
    Single<Request> requestNewRecord();

    Completable initialiseRepositoryIfFirstStart();
    Completable initialiseBitmaps();

    Single<Bitmap> getBitmap(String code);
    Single<List<CountryFlagBitmap>> getAllBitmaps();

    Single<List<Currency>> requestFullCurrencyList();
    Single<List<Currency>> requestFilteredCurrencyList();

    Completable saveRatePair(Pair<Currency, Currency> pair);
    Observable<List<CurrencyExchangeRateWithId>> getAllSavedRecords();
    void swapRecord(String recordId);
    void resetAllSavedRecords();

    void dispose();

}
