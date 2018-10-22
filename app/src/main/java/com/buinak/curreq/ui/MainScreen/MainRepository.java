package com.buinak.curreq.ui.MainScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.CountryFlagBitmap;
import com.buinak.curreq.entities.CurreqEntity.Currency;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRate;

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

    public LiveData<List<CurrencyExchangeRate>> getSavedRatesLiveData(){

        MutableLiveData<List<CurrencyExchangeRate>> liveData = new MutableLiveData<>();
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

    private List<CurrencyExchangeRate> wrapRateRecords(List<CountryFlagBitmap> wrappers,
                                                                       List<CurrencyExchangeRate> records){
        HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();
        for (CountryFlagBitmap wrapper :
                wrappers) {
            bitmapHashMap.put(wrapper.getCode(), wrapper.getBitmap());
        }
        List<CurrencyExchangeRate> currencyExchangeRateWithBitmapsAndIds = new ArrayList<>();
        for (CurrencyExchangeRate currencyExchangeRateWithId :
                records) {
            Currency baseCurrency = new Currency(currencyExchangeRateWithId.getBaseCurrency(),
                    bitmapHashMap.get(currencyExchangeRateWithId.getBaseCurrency().getCode().substring(0, 2).toLowerCase()));

            Currency currency = new Currency(currencyExchangeRateWithId.getCurrency(),
                    bitmapHashMap.get(currencyExchangeRateWithId.getCurrency().getCode().substring(0, 2).toLowerCase()));

            CurrencyExchangeRate currencyExchangeRateWithBitmapsAndId = new CurrencyExchangeRate(currencyExchangeRateWithId.getId(),
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
