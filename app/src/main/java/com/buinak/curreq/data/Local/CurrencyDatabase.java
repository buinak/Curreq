package com.buinak.curreq.data.Local;

import android.util.Pair;

import com.buinak.curreq.entities.CurreqEntity.Currency;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRate;
import com.buinak.curreq.entities.CurreqEntity.Request;
import com.buinak.curreq.entities.RealmEntity.RealmCurrency;
import com.buinak.curreq.entities.RealmEntity.RealmCurrencyExchangeRate;
import com.buinak.curreq.entities.RealmEntity.RealmRequest;
import com.buinak.curreq.entities.RealmEntity.RealmAddedRate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class CurrencyDatabase {

    private CompositeDisposable disposable;

    public CurrencyDatabase() {
        disposable = new CompositeDisposable();
    }

    @NonNull
    public Single<List<Currency>> getAllCurrencies() {
        Realm realm = null;
        List<Currency> currencies = new ArrayList<>();
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<RealmCurrency> realmResults = realm.where(RealmCurrency.class)
                    .findAll();
            for (RealmCurrency realmCurrency :
                    realmResults) {
                currencies.add(new Currency(realmCurrency.getCode(), realmCurrency.getName()));
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return Single.just(currencies);
    }

    @NonNull
    public Single<Request> getLatestRealmRecord() {
        Realm realm = null;
        Request record;
        try {
            realm = Realm.getDefaultInstance();
            record = processRecord(realm.copyFromRealm(realm.where(RealmRequest.class).findAll().last()));
            System.out.println();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return Single.just(record);
    }

    private Request processRecord(RealmRequest record) {
        Request newRecord = new Request();
        if (record != null) {
            try (Realm realm = Realm.getDefaultInstance()) {
                newRecord.setDate(record.getDate());
                List<CurrencyExchangeRate> records = new ArrayList<>();
                for (RealmCurrencyExchangeRate rateRecord :
                        record.getRealmCurrencyExchangeRates()) {

                    RealmCurrency baseCurrencyRecord = realm.where(RealmCurrency.class)
                            .equalTo("id", rateRecord.getBaseCurrencyId())
                            .findFirst();
                    Currency baseCurrency = new Currency(baseCurrencyRecord.getCode(),
                            baseCurrencyRecord.getName());

                    RealmCurrency currencyRecord = realm.where(RealmCurrency.class)
                            .equalTo("id", rateRecord.getCurrencyId())
                            .findFirst();
                    Currency currency = new Currency(currencyRecord.getCode(),
                            currencyRecord.getName());

                    records.add(new CurrencyExchangeRate(null, currency, baseCurrency, rateRecord.getValue(), null));
                }
                newRecord.setCurrencyExchangeRates(records);
            }
        }
        return newRecord;
    }

    public void saveRecord(Request record) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> {
                r.where(RealmRequest.class).findAll().deleteAllFromRealm();
                RealmRequest requestRecord = new RealmRequest();
                requestRecord.setDate(record.getDate());
                RealmList<RealmCurrencyExchangeRate> list = new RealmList<>();
                for (CurrencyExchangeRate currencyExchangeRate :
                        record.getCurrencyExchangeRates()) {
                    String baseCurrencyId = r.where(RealmCurrency.class)
                            .equalTo("code", currencyExchangeRate.getBaseCurrency().getCode())
                            .findFirst()
                            .getId();

                    String currencyId = r.where(RealmCurrency.class)
                            .equalTo("code", currencyExchangeRate.getCurrency().getCode())
                            .findFirst()
                            .getId();

                    list.add(new RealmCurrencyExchangeRate(currencyId, baseCurrencyId, currencyExchangeRate.getRate()));
                }
                requestRecord.setRealmCurrencyExchangeRates(list);

                r.copyToRealm(requestRecord);
            });
        }
    }

    public void saveCurrencies(List<Currency> currencyList) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> {
                if (r.where(RealmCurrency.class).findAll().size() > 0) {
                    return;
                }
                for (Currency record :
                        currencyList) {
                    if (r.where(RealmCurrency.class)
                            .equalTo("code", record.getCode())
                            .findAll()
                            .size() == 0) {

                        RealmCurrency realmCurrency =
                                new RealmCurrency(record.getCode(), record.getName());

                        r.copyToRealm(realmCurrency);
                    }
                }
            });
        }
    }

    public void saveRate(Pair<Currency, Currency> pair) {
        try (Realm realm = Realm.getDefaultInstance()) {
            String baseCurrencyId = realm.where(RealmCurrency.class).equalTo("code", pair.first.getCode()).findFirst().getId();
            String currencyId = realm.where(RealmCurrency.class).equalTo("code", pair.second.getCode()).findFirst().getId();

            RealmAddedRate record = new RealmAddedRate(baseCurrencyId, currencyId);
            realm.executeTransaction(r -> r.copyToRealm(record));
        }
    }


    private double getRate(String baseCurrencyId, String currencyId) {
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmCurrency realmBaseCurrency = realm.where(RealmCurrency.class)
                    .equalTo("id", baseCurrencyId)
                    .findAll()
                    .last();
            RealmCurrency realmCurrency = realm.where(RealmCurrency.class)
                    .equalTo("id", currencyId)
                    .findAll()
                    .last();

            if (realmBaseCurrency.getCode().equalsIgnoreCase("EUR")) {
                double rate = realm.where(RealmCurrencyExchangeRate.class)
                        .equalTo("currencyId", currencyId)
                        .findAll().last()
                        .getValue();
                return rate;
            } else if (realmCurrency.getCode().equalsIgnoreCase("EUR")) {
                double initRate = realm.where(RealmCurrencyExchangeRate.class)
                        .equalTo("currencyId", baseCurrencyId)
                        .findAll().last()
                        .getValue();
                double rate = 1 / initRate;
                return rate;
            } else {

                double realmBaseRecord = realm.where(RealmCurrencyExchangeRate.class)
                        .equalTo("currencyId", baseCurrencyId)
                        .findAll().last()
                        .getValue();

                double realmCurrencyRecord = realm.where(RealmCurrencyExchangeRate.class)
                        .equalTo("currencyId", currencyId)
                        .findAll().last()
                        .getValue();

                return realmCurrencyRecord / realmBaseRecord;
            }
        }
    }

    private double getPreviousRate(String baseCurrencyId, String currencyId) {
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmCurrency realmBaseCurrency = realm.where(RealmCurrency.class)
                    .equalTo("id", baseCurrencyId)
                    .findAll()
                    .last();
            RealmCurrency realmCurrency = realm.where(RealmCurrency.class)
                    .equalTo("id", currencyId)
                    .findAll()
                    .last();

            if (realmBaseCurrency.getCode().equalsIgnoreCase("EUR")) {
                RealmResults<RealmCurrencyExchangeRate> rates = realm.where(RealmCurrencyExchangeRate.class)
                        .equalTo("currencyId", currencyId)
                        .findAll();
                if (rates.size() <= 1) return -1;
                return rates.get(rates.size() - 2).getValue();
            } else if (realmCurrency.getCode().equalsIgnoreCase("EUR")) {
                RealmResults<RealmCurrencyExchangeRate> rates = realm.where(RealmCurrencyExchangeRate.class)
                        .equalTo("currencyId", baseCurrencyId)
                        .findAll();
                if (rates.size() <= 1) return -1;
                double initRate = rates.get(rates.size() - 2).getValue();
                double rate = 1 / initRate;
                return rate;
            } else {
                RealmResults<RealmCurrencyExchangeRate> ratesBase = realm.where(RealmCurrencyExchangeRate.class)
                        .equalTo("currencyId", baseCurrencyId)
                        .findAll();
                RealmResults<RealmCurrencyExchangeRate> ratesTarget = realm.where(RealmCurrencyExchangeRate.class)
                        .equalTo("currencyId", currencyId)
                        .findAll();
                if (ratesBase.size() <= 1) return -1;
                double realmBaseRecord = ratesBase.get(ratesBase.size() - 2).getValue();
                double realmCurrencyRecord = ratesTarget.get(ratesTarget.size() - 2).getValue();

                return realmCurrencyRecord / realmBaseRecord;
            }
        }
    }

    private Currency getCurrencyRecord(String currencyId) {
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmCurrency realmCurrency = realm.where(RealmCurrency.class)
                    .equalTo("id", currencyId)
                    .findFirst();
            return new Currency(realmCurrency.getCode(), realmCurrency.getName());
        }
    }

    public Observable<List<CurrencyExchangeRate>> getAllSavedRecords() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }

        disposable = new CompositeDisposable();
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmQuery<RealmAddedRate> rateQuery = realm.where(RealmAddedRate.class);
            RealmQuery<RealmRequest> requestQuery = realm.where(RealmRequest.class);

            PublishSubject<List<CurrencyExchangeRate>> subject = PublishSubject.create();
            Flowable<List<CurrencyExchangeRate>> normalFlowable = rateQuery.findAllAsync()
                    .asFlowable()
                    .map(this::getCurrencyExchangeRates);

            Flowable<List<CurrencyExchangeRate>> rateUpdateFlowable = requestQuery.findAllAsync()
                    .asFlowable()
                    .map(rates -> getCurrencyExchangeRates(rateQuery.findAllAsync()));

            disposable.add(normalFlowable.mergeWith(rateUpdateFlowable)
                    .subscribe(subject::onNext));

            return subject;
        }
    }

    @NonNull
    private List<CurrencyExchangeRate> getCurrencyExchangeRates(List<RealmAddedRate> list) {
        List<CurrencyExchangeRate> newList = new ArrayList<>(list.size());
        for (RealmAddedRate result :
                list) {
            double rate = getRate(result.getBaseCurrencyId(), result.getCurrencyId());
            double previousRate = getPreviousRate(result.getBaseCurrencyId(), result.getCurrencyId());

            Currency baseCurrency = getCurrencyRecord(result.getBaseCurrencyId());
            Currency currency = getCurrencyRecord(result.getCurrencyId());

            CurrencyExchangeRate currencyExchangeRateWithId =
                    new CurrencyExchangeRate(result.getId(), baseCurrency, currency, rate, previousRate);
            newList.add(currencyExchangeRateWithId);
        }
        return newList;
    }

    public void resetAllSavedRecords() {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> {
                r.where(RealmAddedRate.class).findAll().deleteAllFromRealm();
            });
        }
    }

    public boolean hasCurrencyRateRecords() {
        Realm realm = null;
        boolean result = false;
        try {
            realm = Realm.getDefaultInstance();
            if (realm.where(RealmRequest.class).findAll().size() > 0) {
                result = true;
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return result;
    }

    public boolean hasCurrencyRecords() {
        Realm realm = null;
        boolean result = false;
        try {
            realm = Realm.getDefaultInstance();
            if (realm.where(RealmCurrency.class).findAll().size() > 0) {
                result = true;
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return result;
    }

    public void swapRecord(String recordId) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmAddedRate record = realm.where(RealmAddedRate.class).equalTo("id", recordId).findFirst();
            String baseCurrencyId = record.getBaseCurrencyId();
            String currencyId = record.getCurrencyId();
            record.setBaseCurrencyId(currencyId);
            record.setCurrencyId(baseCurrencyId);
            realm.commitTransaction();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    public Observable<Date> getLatestRecordDate() {
            try (Realm realm = Realm.getDefaultInstance()) {
                BehaviorSubject dateObservable = BehaviorSubject.create();
                disposable.add(realm.where(RealmRequest.class).findAllAsync()
                        .asFlowable()
                        .map(results -> results.last())
                        .map(RealmRequest::getDate)
                        .subscribe(date -> {
                            dateObservable.onNext(date);
                        }));
                return dateObservable;
            }
        }
}
