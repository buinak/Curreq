package com.buinak.curreq.entities.CurreqEntity;

import android.graphics.Bitmap;

/**
 * Created by Foreseer on 11-Oct-18.
 */

public class Currency {

    private Bitmap bitmap;

    private String code;
    private String name;

    public Currency(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Currency(Currency currency, Bitmap bitmap) {
        this.code = currency.getCode();
        this.name = currency.getName();

        this.bitmap = bitmap;
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
