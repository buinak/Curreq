package com.buinak.curreq.entities.CurreqEntity;

public class SavedRateRecordBitmapWrapper {

    private String id;
    private CurrencyRecordBitmapWrapper baseCurrencyRecord;
    private CurrencyRecordBitmapWrapper currencyRecord;

    private double rate;

    public SavedRateRecordBitmapWrapper(String id, CurrencyRecordBitmapWrapper baseCurrencyRecord, CurrencyRecordBitmapWrapper currencyRecord, double rate) {
        this.id = id;
        this.baseCurrencyRecord = baseCurrencyRecord;
        this.currencyRecord = currencyRecord;
        this.rate = rate;
    }

    public SavedRateRecordBitmapWrapper() {
    }

    public CurrencyRecordBitmapWrapper getBaseCurrencyRecord() {
        return baseCurrencyRecord;
    }

    public CurrencyRecordBitmapWrapper getCurrencyRecord() {
        return currencyRecord;
    }

    public double getRate() {
        return rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBaseCurrencyRecord(CurrencyRecordBitmapWrapper baseCurrencyRecord) {
        this.baseCurrencyRecord = baseCurrencyRecord;
    }

    public void setCurrencyRecord(CurrencyRecordBitmapWrapper currencyRecord) {
        this.currencyRecord = currencyRecord;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
