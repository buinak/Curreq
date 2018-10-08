package com.buinak.curreq.Entities;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;

public class RateRequestRecord {

    private List<RateRecord> rateRecords;
    private String date;


    public RateRequestRecord() {
    }

    public RateRequestRecord(List<RateRecord> rateRecord, String date) {
        this.rateRecords = rateRecord;
        this.date = date;
    }

    public List<RateRecord> getRateRecords() {
        return rateRecords;
    }

    public void setRateRecords(List<RateRecord> rateRecord) {
        this.rateRecords = rateRecord;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
