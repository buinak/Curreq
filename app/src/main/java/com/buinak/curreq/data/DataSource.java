package com.buinak.curreq.data;

import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.List;

public interface DataSource {
    void requestRecord();
    void requestNewRecord();

    void requestCurrencyList();

    void dispose();

    void setListener(DataSourceListener listener);

    interface DataSourceListener {
        void onRateRequestRecordReceived(RateRequestRecord record);
        void onCurrencyRecordsReceived(List<CurrencyRecord> records);
    }
}
