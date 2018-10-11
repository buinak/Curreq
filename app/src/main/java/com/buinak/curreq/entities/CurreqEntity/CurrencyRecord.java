package com.buinak.curreq.entities.CurreqEntity;

/**
 * Created by Foreseer on 11-Oct-18.
 */

public class CurrencyRecord {
    private String code;
    private String name;

    public CurrencyRecord(String code, String name) {
        this.code = code;
        this.name = name;
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
