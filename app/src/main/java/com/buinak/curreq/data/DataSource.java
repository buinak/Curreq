package com.buinak.curreq.data;

import android.arch.lifecycle.LiveData;
import android.graphics.Bitmap;
import android.util.Pair;

import com.buinak.curreq.entities.CurreqEntity.BitmapWrapper;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;
import com.buinak.curreq.entities.CurreqEntity.SavedRateRecord;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DataSource {

    Single<RateRequestRecord> requestRecord();
    Single<RateRequestRecord> requestNewRecord();

    Completable initialiseRepositoryIfFirstStart();
    Completable initialiseBitmaps();

    Single<Bitmap> getBitmap(String code);
    Single<List<BitmapWrapper>> getAllBitmaps();

    Single<List<CurrencyRecord>> requestFullCurrencyList();
    Single<List<CurrencyRecord>> requestFilteredCurrencyList();

    Completable saveRatePair(Pair<CurrencyRecord, CurrencyRecord> pair);
    LiveData<List<SavedRateRecord>> getAllSavedRecords();

    void dispose();

}
