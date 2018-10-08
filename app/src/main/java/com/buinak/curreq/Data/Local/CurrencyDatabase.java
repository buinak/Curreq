package com.buinak.curreq.Data.Local;

import com.buinak.curreq.Entities.RateRequestRecord;

import io.reactivex.Single;

public class CurrencyDatabase implements LocalDataSource {

    private static LocalDataSource instance;

    public LocalDataSource getInstance(){
        if (instance == null){
            instance = new CurrencyDatabase();
        }
        return instance;
    }

    @Override
    public Single<RateRequestRecord> getLatestRecord() {
        return null;
    }

    @Override
    public void saveRecord(RateRequestRecord record) {

    }
}
