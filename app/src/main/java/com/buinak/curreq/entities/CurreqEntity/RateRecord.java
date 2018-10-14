package com.buinak.curreq.entities.CurreqEntity;

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

    public void setCurrency(CurrencyRecord currency) {
        this.currency = currency;
    }

    public void setBaseCurrency(CurrencyRecord baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}

