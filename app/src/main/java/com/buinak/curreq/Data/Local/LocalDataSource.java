package com.buinak.curreq.Data.Local;

import com.buinak.curreq.Entities.CurreqEntity.RateRequestRecord;

import io.reactivex.Single;

public interface LocalDataSource {
    Single<RateRequestRecord> getLatestRecord();
    void saveRecord(RateRequestRecord record);

    boolean hasRecords();
}
