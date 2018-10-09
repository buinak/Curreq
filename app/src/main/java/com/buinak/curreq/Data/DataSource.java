package com.buinak.curreq.Data;

import com.buinak.curreq.Entities.CurreqEntity.RateRequestRecord;

import io.reactivex.Single;

public interface DataSource {
    void requestRecord();
    void requestNewRecord();

    void dispose();

    interface DataSourceListener {
        void onRateRequestRecordReceived(RateRequestRecord record);
    }
}
