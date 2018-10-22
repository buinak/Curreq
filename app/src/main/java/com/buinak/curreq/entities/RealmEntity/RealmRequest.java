package com.buinak.curreq.entities.RealmEntity;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmRequest extends RealmObject {

    private RealmList<RealmCurrencyExchangeRate> realmCurrencyExchangeRates;
    private Date date;


    public RealmRequest() {
    }


    public RealmRequest(RealmList<RealmCurrencyExchangeRate> realmCurrencyExchangeRates, Date date) {
        this.realmCurrencyExchangeRates = realmCurrencyExchangeRates;
        this.date = date;
    }

    public RealmList<RealmCurrencyExchangeRate> getRealmCurrencyExchangeRates() {
        return realmCurrencyExchangeRates;
    }

    public void setRealmCurrencyExchangeRates(RealmList<RealmCurrencyExchangeRate> realmCurrencyExchangeRates) {
        this.realmCurrencyExchangeRates = realmCurrencyExchangeRates;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
