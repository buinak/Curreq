package com.buinak.curreq.Entities.RealmEntity;

import io.realm.RealmObject;

public class RealmRateRecord extends RealmObject {

    private String currency;
    private Double value;

    public RealmRateRecord(String currency, Double value) {
        this.currency = currency;
        this.value = value;
    }

    public RealmRateRecord() {
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

