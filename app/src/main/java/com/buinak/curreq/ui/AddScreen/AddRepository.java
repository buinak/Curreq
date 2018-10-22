package com.buinak.curreq.ui.AddScreen;

import android.graphics.Bitmap;
import android.util.Pair;

import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.CountryFlagBitmap;
import com.buinak.curreq.entities.CurreqEntity.CurrencyCountryFlagWrapper;
import com.buinak.curreq.entities.CurreqEntity.Currency;

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

    public Single<List<CurrencyCountryFlagWrapper>> getBitmappedCurrencyRecords(){
        return getCurrencyList()
                .zipWith(getBitmaps(), (currencyRecords, bitmapWrappers) -> {
            List<CurrencyCountryFlagWrapper> currencyRecordBitmapWrappers = new ArrayList<>();
            for (Currency currency : currencyRecords) {
                Bitmap bitmap = null;
                for (CountryFlagBitmap countryFlagBitmap :  bitmapWrappers) {
                    if (countryFlagBitmap.getCode().equalsIgnoreCase(currency.getCode().substring(0, 2))){
                        bitmap = countryFlagBitmap.getBitmap();
                        break;
                    }
                }
                currencyRecordBitmapWrappers.add(new CurrencyCountryFlagWrapper(currency, bitmap));
            }
            return currencyRecordBitmapWrappers;
        });
    }

    public Single<List<CountryFlagBitmap>> getBitmaps(){
        return dataSource.getAllBitmaps();
    }

    public Single<List<Currency>> getCurrencyList(){
        return dataSource.requestFilteredCurrencyList();
    }

    public void saveRatePair(Pair<Currency, Currency> pair){
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
