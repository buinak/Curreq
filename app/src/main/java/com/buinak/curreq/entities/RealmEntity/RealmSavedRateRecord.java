package com.buinak.curreq.entities.RealmEntity;

import io.realm.RealmObject;

public class RealmSavedRateRecord extends RealmObject {

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

}
