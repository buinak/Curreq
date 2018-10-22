package com.buinak.curreq.entities.CurreqEntity;

import java.util.Date;
import java.util.List;

public class Request {

    private List<CurrencyExchangeRate> currencyExchangeRates;

    private Date date;

    public Request() {
    }

    public Request(List<CurrencyExchangeRate> currencyExchangeRate, Date date) {
        this.currencyExchangeRates = currencyExchangeRate;
        this.date = date;
    }

    public List<CurrencyExchangeRate> getCurrencyExchangeRates() {
        return currencyExchangeRates;
    }

    public void setCurrencyExchangeRates(List<CurrencyExchangeRate> currencyExchangeRate) {
        this.currencyExchangeRates = currencyExchangeRate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
