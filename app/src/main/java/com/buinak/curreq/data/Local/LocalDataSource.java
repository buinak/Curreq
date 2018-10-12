package com.buinak.curreq.data.Local;

import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.List;

import io.reactivex.Single;

public interface LocalDataSource {
    Single<RateRequestRecord> getLatestRecord();
    Single<List<CurrencyRecord>> getCurrencyList();

    void saveRecord(RateRequestRecord record);
    void saveCurrencies(List<CurrencyRecord> currencyRecordList);

    boolean hasCurrencyRateRecords();
    boolean hasCurrencyRecords();
}
