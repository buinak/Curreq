package com.buinak.curreq.entities.RealmEntity;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmCurrencyExchangeRate extends RealmObject {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    private String currencyId;
    private String baseCurrencyId;
    private Double value;

    public RealmCurrencyExchangeRate(String currencyId, String baseCurrencyId, Double value) {
        this.currencyId = currencyId;
        this.baseCurrencyId = baseCurrencyId;
        this.value = value;
    }

    public RealmCurrencyExchangeRate() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getBaseCurrencyId() {
        return baseCurrencyId;
    }

    public void setBaseCurrencyId(String baseCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}

