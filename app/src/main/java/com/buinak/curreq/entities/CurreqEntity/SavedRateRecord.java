package com.buinak.curreq.entities.CurreqEntity;

public class SavedRateRecord {

    private CurrencyRecord baseCurrencyRecord;
    private CurrencyRecord currencyRecord;

    private double rate;

    public SavedRateRecord(CurrencyRecord baseCurrencyRecord, CurrencyRecord currencyRecord, double rate) {
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
}
