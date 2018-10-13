package com.buinak.curreq.data.Local;

import android.support.annotation.NonNull;

import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;
import com.buinak.curreq.entities.RealmEntity.RealmCurrencyRecord;
import com.buinak.curreq.entities.RealmEntity.RealmRateRecord;
import com.buinak.curreq.entities.RealmEntity.RealmRateRequestRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class CurrencyDatabase implements LocalDataSource {

    public CurrencyDatabase() {
    }

    @Override
    public Single<RateRequestRecord> getLatestRecord() {
        return Single.just(getLatestRealmRecord());
    }

    @Override
    public Single<List<CurrencyRecord>> getCurrencyList() {
        return Single.just(getAllCurrencies());
    }

    @NonNull
    private List<CurrencyRecord> getAllCurrencies() {
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
    private RateRequestRecord getLatestRealmRecord() {
        Realm realm = null;
        RateRequestRecord record;
        try {
            realm = Realm.getDefaultInstance();
            record = processRecord(realm.where(RealmRateRequestRecord.class).findAll().last());
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

    @Override
    public void saveRecord(RateRequestRecord record) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> {
                RealmRateRequestRecord requestRecord = new RealmRateRequestRecord();
                requestRecord.setDate(record.getDate());
                RealmList<RealmRateRecord> list = new RealmList<>();
                for (RateRecord rateRecord :
                        record.getRateRecords()) {
                    long baseCurrencyId = r.where(RealmCurrencyRecord.class)
                            .equalTo("code", rateRecord.getBaseCurrency().getCode())
                            .findFirst()
                            .getId();

                    long currencyId = r.where(RealmCurrencyRecord.class)
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

    @Override
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

                        //generate a new, unique UUID
                        long newUUID = UUID.randomUUID().getMostSignificantBits();
                        //make sure there's no other records with the same UUID, there can't be any collision
                        while ((r.where(RealmCurrencyRecord.class)
                                .equalTo("id", newUUID)
                                .findAll()).size() != 0) {
                            newUUID = UUID.randomUUID().getMostSignificantBits();
                        }

                        RealmCurrencyRecord realmCurrencyRecord =
                                new RealmCurrencyRecord(newUUID,
                                        record.getCode(),
                                        record.getName());

                        r.copyToRealm(realmCurrencyRecord);
                    }
                }
            });
        }
    }

    @Override
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

    @Override
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
}
