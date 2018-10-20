package com.buinak.curreq.entities.CurreqEntity;

public class SavedRateRecord {

    private String id;
    private CurrencyRecord baseCurrencyRecord;
    private CurrencyRecord currencyRecord;

    private double rate;

    public SavedRateRecord(String id, CurrencyRecord baseCurrencyRecord, CurrencyRecord currencyRecord, double rate) {
        this.id = id;
        this.baseCurrencyRecord = baseCurrencyRecord;
        this.currencyRecord = currencyRecord;
        this.rate = rate;
    }

    public CurrencyRecord getBaseCurrencyRecord() {
        return baseCurrencyRecord;
    }

    public CurrencyRecord getCurrencyRecord() {
        return currencyRecord;
    }

    public double getRate() {
        return rate;
    }

    public String getId() {
        return id;
    }
}
