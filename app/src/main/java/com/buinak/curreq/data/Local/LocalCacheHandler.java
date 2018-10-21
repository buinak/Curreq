package com.buinak.curreq.data.Local;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.buinak.curreq.entities.CurreqEntity.BitmapWrapper;
import com.buinak.curreq.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class LocalCacheHandler {

    private Context context;

    private List<BitmapWrapper> bitmaps;

    public LocalCacheHandler(Context context) {
        this.context = context;
    }

    public Completable initialiseBitmaps() {
        bitmaps = new ArrayList<>();

        return Completable.fromCallable(this::loadBitmaps)
                .subscribeOn(Schedulers.computation());
    }


    public Single<Bitmap> getBitmap(String code) {
        for (BitmapWrapper wrapper :
                bitmaps) {
            if (wrapper.getCode().equalsIgnoreCase(code)) {
                return Single.just(wrapper.getBitmap());
            }
        }
        return Single.just(bitmaps.get(0).getBitmap());
    }

    public Single<List<BitmapWrapper>> getBitmaps() {
        if (bitmaps == null){
            return loadBitmaps().doOnSuccess(result -> bitmaps = result);
        } else if (bitmaps.get(0) == null){
            return loadBitmaps().doOnSuccess(result -> bitmaps = result);
        } else {
            return Single.just(bitmaps);
        }
    }

    private Single<List<BitmapWrapper>> loadBitmaps() {
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
        return Single.just(bitmaps);
    }
}
