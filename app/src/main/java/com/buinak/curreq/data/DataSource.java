package com.buinak.curreq.data;

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

public interface DataSource {

    //// REMOTE REPO
    Single<Request> requestNewRecord();
    Completable updateRecords();

    //LOCAL/REMOTE
    Single<Request> requestRecord();
    Completable initialiseRepositoryIfFirstStart();
    Single<List<Currency>> requestFullCurrencyList();
    Single<List<Currency>> requestFilteredCurrencyList();

    //// LOCAL REPO
    Single<Bitmap> getBitmap(String code);
    Single<List<CountryFlagBitmap>> getAllBitmaps();
    Observable<Date> getLatestRecordDateObservable();
    Completable initialiseBitmaps();
    Completable saveRatePair(Pair<Currency, Currency> pair);

    Completable saveDebugPassword(String password);
    Single<Boolean> isDebugPasswordCorrect();

    boolean isDailyUpdatesOn();
    void setDailyUpdates(Boolean dailyUpdates);

    Observable<List<CurrencyExchangeRate>> getAllSavedRecordsObservable();
    void swapRecord(String recordId);
    void resetAllSavedRecords();
}
