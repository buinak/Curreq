package com.buinak.curreq.entities.RealmEntity;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Foreseer on 11-Oct-18.
 */

public class RealmCurrencyRecord extends RealmObject {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    private String code;
    private String name;

    public RealmCurrencyRecord(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public RealmCurrencyRecord() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

