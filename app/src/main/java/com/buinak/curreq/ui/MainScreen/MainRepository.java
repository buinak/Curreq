package com.buinak.curreq.ui.MainScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.CountryFlagBitmap;
import com.buinak.curreq.entities.CurreqEntity.CurrencyCountryFlagWrapper;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRateWithId;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRateWithBitmapsAndId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainRepository {

    private DataSource dataSource;

    private CompositeDisposable disposable;

    public MainRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        disposable = new CompositeDisposable();
    }

    public LiveData<List<CurrencyExchangeRateWithBitmapsAndId>> getSavedRatesLiveData(){

        MutableLiveData<List<CurrencyExchangeRateWithBitmapsAndId>> liveData = new MutableLiveData<>();
        disposable.add(dataSource.getAllSavedRecords().subscribe(results -> {
            disposable.add(dataSource.getAllBitmaps()
                    .subscribeOn(Schedulers.io())
                    .subscribe(bitmaps -> {
                    if (bitmaps.size() != 0){
                        if (bitmaps.get(0) != null) {
                            liveData.postValue(wrapRateRecords(bitmaps, results));
                        } else {
                            liveData.postValue(null);
                        }
                    } else {
                        liveData.postValue(null);
                    }
            }));
        }));
        return liveData;
    }

    private List<CurrencyExchangeRateWithBitmapsAndId> wrapRateRecords(List<CountryFlagBitmap> wrappers,
                                                                       List<CurrencyExchangeRateWithId> records){
        HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();
        for (CountryFlagBitmap wrapper :
                wrappers) {
            bitmapHashMap.put(wrapper.getCode(), wrapper.getBitmap());
        }
        List<CurrencyExchangeRateWithBitmapsAndId> currencyExchangeRateWithBitmapsAndIds = new ArrayList<>();
        for (CurrencyExchangeRateWithId currencyExchangeRateWithId :
                records) {
            CurrencyCountryFlagWrapper baseCurrency = new CurrencyCountryFlagWrapper(currencyExchangeRateWithId.getBaseCurrency(),
                    bitmapHashMap.get(currencyExchangeRateWithId.getBaseCurrency().getCode().substring(0, 2).toLowerCase()));

            CurrencyCountryFlagWrapper currency = new CurrencyCountryFlagWrapper(currencyExchangeRateWithId.getCurrency(),
                    bitmapHashMap.get(currencyExchangeRateWithId.getCurrency().getCode().substring(0, 2).toLowerCase()));

            CurrencyExchangeRateWithBitmapsAndId currencyExchangeRateWithBitmapsAndId = new CurrencyExchangeRateWithBitmapsAndId(currencyExchangeRateWithId.getId(),
                    baseCurrency, currency, currencyExchangeRateWithId.getRate());
            currencyExchangeRateWithBitmapsAndIds.add(currencyExchangeRateWithBitmapsAndId);
        }
        return currencyExchangeRateWithBitmapsAndIds;
    }

    public void replaceRecordWithNew(String recordId){
        dataSource.swapRecord(recordId);
    }

    public void onResetPressed(){
        dataSource.resetAllSavedRecords();
    }
}
