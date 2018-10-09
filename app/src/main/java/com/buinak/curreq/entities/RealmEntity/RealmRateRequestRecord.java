package com.buinak.curreq.entities.RealmEntity;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmRateRequestRecord extends RealmObject {

    private RealmList<RealmRateRecord> realmRateRecords;
    private Date date;


    public RealmRateRequestRecord() {
    }


    public RealmRateRequestRecord(RealmList<RealmRateRecord> realmRateRecords, Date date) {
        this.realmRateRecords = realmRateRecords;
        this.date = date;
    }

    public RealmList<RealmRateRecord> getRealmRateRecords() {
        return realmRateRecords;
    }

    public void setRealmRateRecords(RealmList<RealmRateRecord> realmRateRecords) {
        this.realmRateRecords = realmRateRecords;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
