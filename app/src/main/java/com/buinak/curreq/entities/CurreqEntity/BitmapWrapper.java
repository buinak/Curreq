package com.buinak.curreq.entities.CurreqEntity;

import android.graphics.Bitmap;

public class BitmapWrapper {
    private String code;
    private Bitmap bitmap;

    public BitmapWrapper(String code, Bitmap bitmap) {
        this.code = code;
        this.bitmap = bitmap;
    }

    public String getCode() {
        return code;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
