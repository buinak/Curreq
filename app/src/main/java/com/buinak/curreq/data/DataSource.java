package com.buinak.curreq.data;

import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

public interface DataSource {
    void requestRecord();
    void requestNewRecord();

    void dispose();

    void setListener(DataSourceListener listener);

    interface DataSourceListener {
        void onRateRequestRecordReceived(RateRequestRecord record);
    }
}
