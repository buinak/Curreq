package com.buinak.curreq.data;

import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.List;

import io.reactivex.Single;

public interface DataSource {
    Single<RateRequestRecord> requestRecord();

    Single<RateRequestRecord> requestNewRecord();

    Single<Boolean> initialiseRepositoryIfFirstStart();

    Single<List<CurrencyRecord>> requestCurrencyList();

    void dispose();

}
