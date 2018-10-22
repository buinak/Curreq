package com.buinak.curreq.entities.CurreqEntity;

public class CurrencyExchangeRate {

    private Currency currency;
    private Currency baseCurrency;
    private Double value;

    public CurrencyExchangeRate(Currency currency, Currency baseCurrency, Double value) {
        this.currency = currency;
        this.baseCurrency = baseCurrency;
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Double getValue() {
        return value;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}

