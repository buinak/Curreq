package com.buinak.curreq.entities.RealmEntity;

import io.realm.RealmObject;

/**
 * Created by Foreseer on 11-Oct-18.
 */

public class RealmCurrencyRecord extends RealmObject {
    private String code;
    private String name;

    public RealmCurrencyRecord(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public RealmCurrencyRecord() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

