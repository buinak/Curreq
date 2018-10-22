package com.buinak.curreq.entities.CurreqEntity;

import android.graphics.Bitmap;

public class CurrencyCountryFlagWrapper extends Currency {
    private Bitmap bitmap;

    private String code;
    private String name;

    public CurrencyCountryFlagWrapper(Currency currency, Bitmap bitmap) {
        super(currency.getCode(), currency.getName());

        this.code = currency.getCode();
        this.name = currency.getName();

        this.bitmap = bitmap;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
