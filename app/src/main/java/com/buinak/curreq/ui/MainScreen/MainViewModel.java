package com.buinak.curreq.ui.MainScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRate;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MainViewModel extends ViewModel {

    @Inject
    MainRepository mainRepository;

    private LiveData<List<CurrencyExchangeRate>> savedRateRecords;
    private LiveData<Date> lastUpdatedDate;

    public MainViewModel() {
        CurreqApplication.inject(this);

        savedRateRecords = mainRepository.getSavedRatesLiveData();
        lastUpdatedDate = mainRepository.getLastUpdatedLiveData();
    }

    public void onBind() {
        mainRepository.checkDailyUpdates();
    }

    public LiveData<List<CurrencyExchangeRate>> getSavedRateRecords() {
        return savedRateRecords;
    }

    public void onRateRecordSwapped(String recordId){
        mainRepository.replaceRecordWithNew(recordId);
    }

    public void onResetPressed(){
        mainRepository.onResetPressed();
    }

    public void onUpdatePressed() {
        mainRepository.onUpdatePressed();
    }

    public LiveData<Date> getLastUpdatedDate() {
        return lastUpdatedDate;
    }
}
