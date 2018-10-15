package com.buinak.curreq.data;

import android.graphics.Bitmap;

import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DataSource {

    Single<RateRequestRecord> requestRecord();

    Single<RateRequestRecord> requestNewRecord();

    Completable initialiseRepositoryIfFirstStart();
    Completable initialiseBitmaps();

    Single<Bitmap> getBitmap(String code);

    Single<List<CurrencyRecord>> requestFullCurrencyList();
    Single<List<CurrencyRecord>> requestFilteredCurrencyList();

    void dispose();

}
