package com.buinak.curreq.data.Local;

import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import io.reactivex.Single;

public interface LocalDataSource {
    Single<RateRequestRecord> getLatestRecord();
    void saveRecord(RateRequestRecord record);

    boolean hasRecords();
}
