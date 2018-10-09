package com.buinak.curreq.Entities.CurreqEntity;

import io.realm.RealmObject;

public class RateRecord {

    private String currency;
    private Double value;

    public RateRecord(String currency, Double value) {
        this.currency = currency;
        this.value = value;
    }

    public RateRecord() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}

