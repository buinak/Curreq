package com.buinak.curreq.entities.CurreqEntity;

public class CurrencyExchangeRateWithBitmapsAndId {

    private String id;
    private CurrencyCountryFlagWrapper baseCurrencyRecord;
    private CurrencyCountryFlagWrapper currencyRecord;

    private double rate;

    public CurrencyExchangeRateWithBitmapsAndId(String id, CurrencyCountryFlagWrapper baseCurrencyRecord, CurrencyCountryFlagWrapper currencyRecord, double rate) {
        this.id = id;
        this.baseCurrencyRecord = baseCurrencyRecord;
        this.currencyRecord = currencyRecord;
        this.rate = rate;
    }

    public CurrencyExchangeRateWithBitmapsAndId() {
    }

    public CurrencyCountryFlagWrapper getBaseCurrencyRecord() {
        return baseCurrencyRecord;
    }

    public CurrencyCountryFlagWrapper getCurrencyRecord() {
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

    public void setBaseCurrencyRecord(CurrencyCountryFlagWrapper baseCurrencyRecord) {
        this.baseCurrencyRecord = baseCurrencyRecord;
    }

    public void setCurrencyRecord(CurrencyCountryFlagWrapper currencyRecord) {
        this.currencyRecord = currencyRecord;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
