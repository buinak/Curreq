package com.buinak.curreq.data.Local;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.buinak.curreq.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;

public class LocalCacheHandler {

    private Context context;

    private Map<String, Bitmap> bitmaps;

    Disposable disposable;

    public LocalCacheHandler(Context context) {
        this.context = context;
        bitmaps = new HashMap<>();
    }

    public Completable initialiseBitmaps() {
        bitmaps = new HashMap<>();

        CompletableSubject completable = CompletableSubject.create();

        disposable = Completable.fromCallable(this::loadBitmaps)
                .subscribeOn(Schedulers.computation())
                .subscribe(completable::onComplete);

        return completable;
    }


    public Bitmap getBitmap(String code){
        Bitmap bitmap = bitmaps.get(code);
        if (bitmap != null) {
            return bitmap;
        } else {
            return bitmaps.entrySet().iterator().next().getValue();
        }
    }

    private Map<String, Bitmap> loadBitmaps() {
        Map<String, Bitmap> map = new HashMap<>();
        for (String code :
                Constants.PERMITTED_CODES) {
            code = code.substring(0, 2).toLowerCase();
            int bitmapId = context.getResources().getIdentifier(code, "drawable", context.getPackageName());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
            if (bitmap != null) {
                Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 100, 60, false);
                bitmap.recycle();
                map.put(code, newBitmap);
            } else {
                map.put(code, bitmap);
            }

        }
        bitmaps = map;
        return map;
    }
}
