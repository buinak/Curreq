package com.buinak.curreq.entities.RealmEntity;

import io.realm.RealmObject;

public class RealmRateRecord extends RealmObject {

    private RealmCurrencyRecord currency;
    private RealmCurrencyRecord baseCurrency;
    private Double value;

    public RealmRateRecord(RealmCurrencyRecord currency, RealmCurrencyRecord baseCurrency, Double value) {
        this.currency = currency;
        this.baseCurrency = baseCurrency;
        this.value = value;
    }

    public RealmRateRecord() {
    }

    public RealmCurrencyRecord getCurrency() {
        return currency;
    }

    public void setCurrency(RealmCurrencyRecord currency) {
        this.currency = currency;
    }

    public RealmCurrencyRecord getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(RealmCurrencyRecord baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}

