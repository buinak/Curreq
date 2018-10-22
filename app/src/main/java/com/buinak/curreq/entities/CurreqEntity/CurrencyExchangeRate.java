package com.buinak.curreq.entities.CurreqEntity;

public class CurrencyExchangeRate {

    private String id;

    private Currency currency;
    private Currency baseCurrency;
    private Double rate;

    public CurrencyExchangeRate(Currency currency, Currency baseCurrency, Double rate) {
        this.currency = currency;
        this.baseCurrency = baseCurrency;
        this.rate = rate;
    }

    public CurrencyExchangeRate(String id, Currency baseCurrencyRecord, Currency currencyRecord, double rate) {
        this.id = id;
        this.baseCurrency = baseCurrencyRecord;
        this.currency = currencyRecord;
        this.rate = rate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Double getRate() {
        return rate;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

