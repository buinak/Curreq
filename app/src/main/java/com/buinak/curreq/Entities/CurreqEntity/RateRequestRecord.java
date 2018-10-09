package com.buinak.curreq.Entities.CurreqEntity;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.annotations.Ignore;

public class RateRequestRecord {

    private List<RateRecord> rateRecords;

    private Date date;

    public RateRequestRecord() {
    }

    public RateRequestRecord(List<RateRecord> rateRecord, Date date) {
        this.rateRecords = rateRecord;
        this.date = date;
    }

    public List<RateRecord> getRateRecords() {
        return rateRecords;
    }

    public void setRateRecords(List<RateRecord> rateRecord) {
        this.rateRecords = rateRecord;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
