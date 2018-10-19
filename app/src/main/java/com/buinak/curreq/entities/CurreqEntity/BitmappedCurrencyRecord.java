package com.buinak.curreq.entities.CurreqEntity;

import android.graphics.Bitmap;

public class BitmappedCurrencyRecord extends CurrencyRecord{
    private Bitmap bitmap;

    private String code;
    private String name;

    public BitmappedCurrencyRecord(CurrencyRecord currencyRecord, Bitmap bitmap) {
        super(currencyRecord.getCode(), currencyRecord.getName());
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
