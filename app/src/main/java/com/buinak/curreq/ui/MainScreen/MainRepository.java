package com.buinak.curreq.ui.MainScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.BitmapWrapper;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecordBitmapWrapper;
import com.buinak.curreq.entities.CurreqEntity.SavedRateRecord;
import com.buinak.curreq.entities.CurreqEntity.SavedRateRecordBitmapWrapper;

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

    public LiveData<List<SavedRateRecordBitmapWrapper>> getSavedRatesLiveData(){

        MutableLiveData<List<SavedRateRecordBitmapWrapper>> liveData = new MutableLiveData<>();
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

    private List<SavedRateRecordBitmapWrapper> wrapRateRecords(List<BitmapWrapper> wrappers,
                                                               List<SavedRateRecord> records){
        HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();
        for (BitmapWrapper wrapper :
                wrappers) {
            bitmapHashMap.put(wrapper.getCode(), wrapper.getBitmap());
        }
        List<SavedRateRecordBitmapWrapper> savedRateRecordBitmapWrappers = new ArrayList<>();
        for (SavedRateRecord savedRateRecord :
                records) {
            CurrencyRecordBitmapWrapper baseCurrency = new CurrencyRecordBitmapWrapper(savedRateRecord.getBaseCurrencyRecord(),
                    bitmapHashMap.get(savedRateRecord.getBaseCurrencyRecord().getCode().substring(0, 2).toLowerCase()));

            CurrencyRecordBitmapWrapper currency = new CurrencyRecordBitmapWrapper(savedRateRecord.getCurrencyRecord(),
                    bitmapHashMap.get(savedRateRecord.getCurrencyRecord().getCode().substring(0, 2).toLowerCase()));

            SavedRateRecordBitmapWrapper savedRateRecordBitmapWrapper = new SavedRateRecordBitmapWrapper(savedRateRecord.getId(),
                    baseCurrency, currency, savedRateRecord.getRate());
            savedRateRecordBitmapWrappers.add(savedRateRecordBitmapWrapper);
        }
        return savedRateRecordBitmapWrappers;
    }

    public void replaceRecordWithNew(String recordId){
        dataSource.swapRecord(recordId);
    }

    public void onResetPressed(){
        dataSource.resetAllSavedRecords();
    }
}
