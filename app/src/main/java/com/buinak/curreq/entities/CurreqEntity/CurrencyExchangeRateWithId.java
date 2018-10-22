package com.buinak.curreq.entities.CurreqEntity;

public class CurrencyExchangeRateWithId {

    private String id;
    private Currency baseCurrency;
    private Currency currency;

    private double rate;

    public CurrencyExchangeRateWithId(String id, Currency baseCurrency, Currency currency, double rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.currency = currency;
        this.rate = rate;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }

    public String getId() {
        return id;
    }
}
