package com.buinak.curreq.entities.CurreqEntity;

import com.buinak.curreq.entities.RealmEntity.RealmCurrencyRecord;

public class RateRecord {

    private CurrencyRecord currency;
    private CurrencyRecord baseCurrency;
    private Double value;

    public RateRecord(CurrencyRecord currency, CurrencyRecord baseCurrency, Double value) {
        this.currency = currency;
        this.baseCurrency = baseCurrency;
        this.value = value;
    }

    public CurrencyRecord getCurrency() {
        return currency;
    }

    public CurrencyRecord getBaseCurrency() {
        return baseCurrency;
    }

    public Double getValue() {
        return value;
    }
}

