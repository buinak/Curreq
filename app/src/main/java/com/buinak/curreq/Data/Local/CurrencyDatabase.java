package com.buinak.curreq.Data.Local;

import android.support.annotation.NonNull;

import com.buinak.curreq.Entities.CurreqEntity.RateRecord;
import com.buinak.curreq.Entities.CurreqEntity.RateRequestRecord;
import com.buinak.curreq.Entities.RealmEntity.RealmRateRecord;
import com.buinak.curreq.Entities.RealmEntity.RealmRateRequestRecord;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.realm.Realm;
import io.realm.RealmList;

public class CurrencyDatabase implements LocalDataSource {

    private static LocalDataSource instance;

    public static LocalDataSource getInstance(){
        if (instance == null){
            instance = new CurrencyDatabase();
        }
        return instance;
    }

    @Override
    public Single<RateRequestRecord> getLatestRecord() {
        return Single.just(getLatestRealmRecord());
    }

    @NonNull
    private RateRequestRecord getLatestRealmRecord(){
        Realm realm = null;
        RateRequestRecord record;
        try {
            realm = Realm.getDefaultInstance();
            record = processRecord(realm.where(RealmRateRequestRecord.class).findAll().last());
        } finally {
            if (realm != null){
                realm.close();
            }
        }
        return record;
    }

    private RateRequestRecord processRecord(RealmRateRequestRecord record){
        RateRequestRecord newRecord = new RateRequestRecord();
        if (record != null) {
            newRecord.setDate(record.getDate());
            List<RateRecord> records = new ArrayList<>();
            for (RealmRateRecord rateRecord :
                 record.getRealmRateRecords()) {
                records.add(new RateRecord(rateRecord.getCurrency(), rateRecord.getValue()));
            }
            newRecord.setRateRecords(records);
        }
        return newRecord;
    }

    @Override
    public void saveRecord(RateRequestRecord record) {
        try (Realm realm = Realm.getDefaultInstance()){
            realm.executeTransaction(r -> {
                RealmRateRequestRecord requestRecord = new RealmRateRequestRecord();
                requestRecord.setDate(record.getDate());
                RealmList<RealmRateRecord> list = new RealmList<>();
                for (RateRecord rateRecord :
                        record.getRateRecords()) {
                    list.add(new RealmRateRecord(rateRecord.getCurrency(), rateRecord.getValue()));
                }
                requestRecord.setRealmRateRecords(list);

                r.copyToRealm(requestRecord);
            });
        }
    }

    @Override
    public boolean hasRecords() {
        Realm realm = null;
        boolean result = false;
        try {
            realm = Realm.getDefaultInstance();
            if (realm.where(RealmRateRequestRecord.class).findAll().size() > 0){
                result = true;
            }
        } finally {
            if (realm != null){
                realm.close();
            }
        }
        return result;
    }
}
