package com.buinak.curreq.data.Local;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.buinak.curreq.entities.CurreqEntity.BitmapWrapper;
import com.buinak.curreq.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;

public class LocalCacheHandler {

    private Context context;

    private List<BitmapWrapper> bitmaps;

    Disposable disposable;

    public LocalCacheHandler(Context context) {
        this.context = context;
        bitmaps = new ArrayList<>();
    }

    public Completable initialiseBitmaps() {
        bitmaps = new ArrayList<>();

        CompletableSubject completable = CompletableSubject.create();

        disposable = Completable.fromCallable(this::loadBitmaps)
                .subscribeOn(Schedulers.computation())
                .subscribe(completable::onComplete);

        return completable;
    }


    public Bitmap getBitmap(String code) {
        for (BitmapWrapper wrapper :
                bitmaps) {
            if (wrapper.getCode().equalsIgnoreCase(code)) {
                return wrapper.getBitmap();
            }
        }
        return bitmaps.get(0).getBitmap();
    }

    public List<BitmapWrapper> getBitmaps() {
        return bitmaps;
    }

    private List<BitmapWrapper> loadBitmaps() {
        bitmaps = new ArrayList<>();
        for (String code :
                Constants.PERMITTED_CODES) {
            code = code.substring(0, 2).toLowerCase();
            int bitmapId = context.getResources().getIdentifier(code, "drawable", context.getPackageName());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
            if (bitmap != null) {
                Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 100, 60, false);
                bitmap.recycle();
                bitmaps.add(new BitmapWrapper(code, newBitmap));
            } else {
                bitmaps.add(new BitmapWrapper(code, bitmap));
            }

        }
        return bitmaps;
    }
}