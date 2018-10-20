package com.buinak.curreq.entities.RealmEntity;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmSavedRateRecord extends RealmObject {

    @PrimaryKey
    String id = UUID.randomUUID().toString();

    private String baseCurrencyId;
    private String currencyId;

    public RealmSavedRateRecord(String baseCurrencyId, String currencyId) {
        this.baseCurrencyId = baseCurrencyId;
        this.currencyId = currencyId;
    }

    public RealmSavedRateRecord() {
    }

    public String getBaseCurrencyId() {
        return baseCurrencyId;
    }

    public void setBaseCurrencyId(String baseCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
