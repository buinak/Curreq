package com.buinak.curreq.data.Remote;

import com.buinak.curreq.entities.CurreqEntity.Currency;
import com.buinak.curreq.entities.CurreqEntity.Request;

import java.util.List;

import io.reactivex.Single;

public interface RemoteDataSource {
    Single<Request> getRates();
    Single<List<Currency>> getCurrencyList();

    void setCurrencyList(List<Currency> currencies);

    boolean isReady();
}
