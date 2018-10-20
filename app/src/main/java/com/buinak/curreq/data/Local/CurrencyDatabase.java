package com.buinak.curreq.data.Local;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;
import com.buinak.curreq.entities.CurreqEntity.SavedRateRecord;
import com.buinak.curreq.entities.RealmEntity.RealmCurrencyRecord;
import com.buinak.curreq.entities.RealmEntity.RealmRateRecord;
import com.buinak.curreq.entities.RealmEntity.RealmRateRequestRecord;
import com.buinak.curreq.entities.RealmEntity.RealmSavedRateRecord;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class CurrencyDatabase {

    private CompositeDisposable disposable;

    public CurrencyDatabase() {
    }

    @NonNull
    public List<CurrencyRecord> getAllCurrencies() {
        Realm realm = null;
        List<CurrencyRecord> currencyRecords = new ArrayList<>();
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<RealmCurrencyRecord> realmResults = realm.where(RealmCurrencyRecord.class)
                    .findAll();
            for (RealmCurrencyRecord realmCurrencyRecord :
                    realmResults) {
                currencyRecords.add(new CurrencyRecord(realmCurrencyRecord.getCode(), realmCurrencyRecord.getName()));
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return currencyRecords;
    }

    @NonNull
    public RateRequestRecord getLatestRealmRecord() {
        Realm realm = null;
        RateRequestRecord record;
        try {
            realm = Realm.getDefaultInstance();
            record = processRecord(realm.copyFromRealm(realm.where(RealmRateRequestRecord.class).findAll().last()));
            System.out.println();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return record;
    }

    private RateRequestRecord processRecord(RealmRateRequestRecord record) {
        RateRequestRecord newRecord = new RateRequestRecord();
        if (record != null) {
            try (Realm realm = Realm.getDefaultInstance()) {
                newRecord.setDate(record.getDate());
                List<RateRecord> records = new ArrayList<>();
                for (RealmRateRecord rateRecord :
                        record.getRealmRateRecords()) {

                    RealmCurrencyRecord baseCurrencyRecord = realm.where(RealmCurrencyRecord.class)
                            .equalTo("id", rateRecord.getBaseCurrencyId())
                            .findFirst();
                    CurrencyRecord baseCurrency = new CurrencyRecord(baseCurrencyRecord.getCode(),
                            baseCurrencyRecord.getName());

                    RealmCurrencyRecord currencyRecord = realm.where(RealmCurrencyRecord.class)
                            .equalTo("id", rateRecord.getCurrencyId())
                            .findFirst();
                    CurrencyRecord currency = new CurrencyRecord(currencyRecord.getCode(),
                            currencyRecord.getName());

                    records.add(new RateRecord(currency, baseCurrency, rateRecord.getValue()));
                }
                newRecord.setRateRecords(records);
            }
        }
        return newRecord;
    }

    public void saveRecord(RateRequestRecord record) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> {
                RealmRateRequestRecord requestRecord = new RealmRateRequestRecord();
                requestRecord.setDate(record.getDate());
                RealmList<RealmRateRecord> list = new RealmList<>();
                for (RateRecord rateRecord :
                        record.getRateRecords()) {
                    String baseCurrencyId = r.where(RealmCurrencyRecord.class)
                            .equalTo("code", rateRecord.getBaseCurrency().getCode())
                            .findFirst()
                            .getId();

                    String currencyId = r.where(RealmCurrencyRecord.class)
                            .equalTo("code", rateRecord.getCurrency().getCode())
                            .findFirst()
                            .getId();

                    list.add(new RealmRateRecord(currencyId, baseCurrencyId, rateRecord.getValue()));
                }
                requestRecord.setRealmRateRecords(list);

                r.copyToRealm(requestRecord);
            });
        }
    }

    public void saveCurrencies(List<CurrencyRecord> currencyRecordList) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> {
                if (r.where(RealmCurrencyRecord.class).findAll().size() > 0) {
                    return;
                }
                for (CurrencyRecord record :
                        currencyRecordList) {
                    if (r.where(RealmCurrencyRecord.class)
                            .equalTo("code", record.getCode())
                            .findAll()
                            .size() == 0) {

                        RealmCurrencyRecord realmCurrencyRecord =
                                new RealmCurrencyRecord(record.getCode(), record.getName());

                        r.copyToRealm(realmCurrencyRecord);
                    }
                }
            });
        }
    }

    public void saveRate(Pair<CurrencyRecord, CurrencyRecord> pair) {
        try (Realm realm = Realm.getDefaultInstance()) {
            String baseCurrencyId = realm.where(RealmCurrencyRecord.class).equalTo("code", pair.first.getCode()).findFirst().getId();
            String currencyId = realm.where(RealmCurrencyRecord.class).equalTo("code", pair.second.getCode()).findFirst().getId();

            RealmSavedRateRecord record = new RealmSavedRateRecord(baseCurrencyId, currencyId);
            realm.executeTransaction(r -> r.copyToRealm(record));
        }
    }


    private double getRate(String baseCurrencyId, String currencyId) {
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmCurrencyRecord realmBaseCurrency = realm.where(RealmCurrencyRecord.class)
                    .equalTo("id", baseCurrencyId)
                    .findFirst();
            RealmCurrencyRecord realmCurrency = realm.where(RealmCurrencyRecord.class)
                    .equalTo("id", currencyId)
                    .findFirst();

            if (realmBaseCurrency.getCode().equalsIgnoreCase("EUR")) {
                double rate = realm.where(RealmRateRecord.class)
                        .equalTo("currencyId", currencyId)
                        .findFirst()
                        .getValue();
                return rate;
            } else if (realmCurrency.getCode().equalsIgnoreCase("EUR")) {
                double initRate = realm.where(RealmRateRecord.class)
                        .equalTo("currencyId", baseCurrencyId)
                        .findFirst()
                        .getValue();
                double rate = 1 / initRate;
                return rate;
            } else {

                double realmBaseRecord = realm.where(RealmRateRecord.class)
                        .equalTo("currencyId", baseCurrencyId)
                        .findFirst()
                        .getValue();

                double realmCurrencyRecord = realm.where(RealmRateRecord.class)
                        .equalTo("currencyId", currencyId)
                        .findFirst()
                        .getValue();

                return realmCurrencyRecord / realmBaseRecord;
            }
        }
    }

    private CurrencyRecord getCurrencyRecord(String currencyId) {
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmCurrencyRecord realmCurrencyRecord = realm.where(RealmCurrencyRecord.class)
                    .equalTo("id", currencyId)
                    .findFirst();
            return new CurrencyRecord(realmCurrencyRecord.getCode(), realmCurrencyRecord.getName());
        }
    }

    Observable<List<SavedRateRecord>> getAllSavedRecords() {
        if (disposable != null){
            disposable.dispose();
            disposable = null;
        }

        disposable = new CompositeDisposable();
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmQuery<RealmSavedRateRecord> records = realm.where(RealmSavedRateRecord.class);
            PublishSubject<List<SavedRateRecord>> publishSubject = PublishSubject.create();
            if (realm.isAutoRefresh()) {
                disposable.add(records.findAllAsync()
                        .asFlowable()
                        .subscribe(list -> {
                            List<SavedRateRecord> newList = new ArrayList<>(list.size());
                            for (RealmSavedRateRecord result :
                                    list) {
                                double rate = getRate(result.getBaseCurrencyId(), result.getCurrencyId());

                                CurrencyRecord baseCurrency = getCurrencyRecord(result.getBaseCurrencyId());
                                CurrencyRecord currency = getCurrencyRecord(result.getCurrencyId());

                                SavedRateRecord savedRateRecord = new SavedRateRecord(result.getId(), baseCurrency, currency, rate);
                                newList.add(savedRateRecord);
                            }
                            publishSubject.onNext(newList);
                        }));
                return publishSubject;
            } else {
                disposable.add(records.findAll()
                        .asFlowable()
                        .subscribe(list -> {
                            List<SavedRateRecord> newList = new ArrayList<>(list.size());
                            for (RealmSavedRateRecord result :
                                    list) {
                                double rate = getRate(result.getBaseCurrencyId(), result.getCurrencyId());

                                CurrencyRecord baseCurrency = getCurrencyRecord(result.getBaseCurrencyId());
                                CurrencyRecord currency = getCurrencyRecord(result.getCurrencyId());

                                SavedRateRecord savedRateRecord = new SavedRateRecord(result.getId(), baseCurrency, currency, rate);
                                newList.add(savedRateRecord);
                            }
                            publishSubject.onNext(newList);
                        }, e -> {
                            System.out.println();
                            System.out.println();
                        }));
                return publishSubject;
            }
        }
    }

    public void resetAllSavedRecords(){
        try (Realm realm = Realm.getDefaultInstance()){
            realm.executeTransaction(r -> {
                r.where(RealmSavedRateRecord.class).findAll().deleteAllFromRealm();
            });
        }
    }

    public boolean hasCurrencyRateRecords() {
        Realm realm = null;
        boolean result = false;
        try {
            realm = Realm.getDefaultInstance();
            if (realm.where(RealmRateRequestRecord.class).findAll().size() > 0) {
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
            if (realm.where(RealmCurrencyRecord.class).findAll().size() > 0) {
                result = true;
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return result;
    }

    public void swapRecord(String recordId){
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmSavedRateRecord record = realm.where(RealmSavedRateRecord.class).equalTo("id", recordId).findFirst();
            String baseCurrencyId = record.getBaseCurrencyId();
            String currencyId = record.getCurrencyId();
            record.setBaseCurrencyId(currencyId);
            record.setCurrencyId(baseCurrencyId);
            realm.commitTransaction();
        } finally {
             if (realm != null){
                 realm.close();
             }
        }
    }
}
