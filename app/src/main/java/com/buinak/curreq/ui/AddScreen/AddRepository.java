package com.buinak.curreq.ui.AddScreen;

import android.graphics.Bitmap;
import android.util.Pair;

import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.BitmapWrapper;
import com.buinak.curreq.entities.CurreqEntity.BitmappedCurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.CompletableSubject;

public class AddRepository {

    private DataSource dataSource;

    private CompletableSubject finished;

    private Disposable disposable;

    public AddRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        finished = CompletableSubject.create();
    }

    public void reset(){
        finished = CompletableSubject.create();
    }

    public Single<List<BitmappedCurrencyRecord>> getBitmappedCurrencyRecords(){
        return getCurrencyList()
                .zipWith(getBitmaps(), (currencyRecords, bitmapWrappers) -> {
            List<BitmappedCurrencyRecord> bitmappedCurrencyRecords = new ArrayList<>();
            for (CurrencyRecord currencyRecord : currencyRecords) {
                Bitmap bitmap = null;
                for (BitmapWrapper bitmapWrapper :  bitmapWrappers) {
                    if (bitmapWrapper.getCode().equalsIgnoreCase(currencyRecord.getCode().substring(0, 2))){
                        bitmap = bitmapWrapper.getBitmap();
                        break;
                    }
                }
                bitmappedCurrencyRecords.add(new BitmappedCurrencyRecord(currencyRecord, bitmap));
            }
            return bitmappedCurrencyRecords;
        });
    }

    public Single<List<BitmapWrapper>> getBitmaps(){
        return dataSource.getAllBitmaps();
    }

    public Single<List<CurrencyRecord>> getCurrencyList(){
        return dataSource.requestFilteredCurrencyList();
    }

    public void saveRatePair(Pair<CurrencyRecord, CurrencyRecord> pair){
        disposable = dataSource.saveRatePair(pair)
                .subscribe(() -> finished.onComplete(), e -> {
                    System.out.println();
                    System.out.println();
                });
    }

    public Completable getFinished() {
        return finished;
    }

    public void dispose(){
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
