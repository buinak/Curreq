package com.buinak.curreq.data.Remote;

import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.List;

import io.reactivex.Single;

public interface RemoteDataSource {
    Single<RateRequestRecord> getRates();
    Single<List<CurrencyRecord>> getCurrencyList();

    boolean isReady();
}
