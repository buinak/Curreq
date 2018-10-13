package com.buinak.curreq.entities.RealmEntity;

import io.realm.RealmObject;

public class RealmRateRecord extends RealmObject {

    private long currencyId;
    private long baseCurrencyId;
    private Double value;

    public RealmRateRecord(long currencyId, long baseCurrencyId, Double value) {
        this.currencyId = currencyId;
        this.baseCurrencyId = baseCurrencyId;
        this.value = value;
    }

    public RealmRateRecord() {
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
    }

    public long getBaseCurrencyId() {
        return baseCurrencyId;
    }

    public void setBaseCurrencyId(long baseCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}

