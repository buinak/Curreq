package com.buinak.curreq.entities.RealmEntity;

import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class RealmSavedRateRecord extends RealmObject {

    private long baseCurrency;
    private long currency;


    @Ignore
    private CurrencyRecord baseCurrencyRecord;

    @Ignore
    private CurrencyRecord currencyRecord;

    @Ignore
    private double rate;

    public RealmSavedRateRecord(long baseCurrency, long currency) {
        this.baseCurrency = baseCurrency;
        this.currency = currency;
    }

    public RealmSavedRateRecord() {
    }

    public long getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(long baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public long getCurrency() {
        return currency;
    }

    public void setCurrency(long currency) {
        this.currency = currency;
    }

    public CurrencyRecord getBaseCurrencyRecord() {
        return baseCurrencyRecord;
    }

    public void setBaseCurrencyRecord(CurrencyRecord baseCurrencyRecord) {
        this.baseCurrencyRecord = baseCurrencyRecord;
    }

    public CurrencyRecord getCurrencyRecord() {
        return currencyRecord;
    }

    public void setCurrencyRecord(CurrencyRecord currencyRecord) {
        this.currencyRecord = currencyRecord;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
