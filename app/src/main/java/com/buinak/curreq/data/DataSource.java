package com.buinak.curreq.data;

import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DataSource {
    List<String> PERMITTED_CODES = Arrays.asList("EUR", "USD", "CZK");

    Single<RateRequestRecord> requestRecord();

    Single<RateRequestRecord> requestNewRecord();

    Completable initialiseRepositoryIfFirstStart();

    Single<List<CurrencyRecord>> requestFullCurrencyList();
    Single<List<CurrencyRecord>> requestFilteredCurrencyList();

    void dispose();

}
