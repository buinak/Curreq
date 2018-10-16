package com.buinak.curreq.entities.CurreqEntity;

import android.graphics.Bitmap;

public class BitmappedCurrencyRecord {
    private Bitmap bitmap;

    private String code;
    private String name;

    public BitmappedCurrencyRecord(CurrencyRecord currencyRecord, Bitmap bitmap) {
        this.code = currencyRecord.getCode();
        this.name = currencyRecord.getName();

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
