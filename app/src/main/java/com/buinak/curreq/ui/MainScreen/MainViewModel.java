package com.buinak.curreq.ui.MainScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRateWithBitmapsAndId;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    @Inject
    MainRepository mainRepository;

    private LiveData<List<CurrencyExchangeRateWithBitmapsAndId>> savedRateRecords;

    public MainViewModel() {
        CurreqApplication.inject(this);

        savedRateRecords = mainRepository.getSavedRatesLiveData();
    }

    public LiveData<List<CurrencyExchangeRateWithBitmapsAndId>> getSavedRateRecords() {
        return savedRateRecords;
    }

    public void onRateRecordSwapped(String recordId){
        mainRepository.replaceRecordWithNew(recordId);
    }

    public void onResetPressed(){
        mainRepository.onResetPressed();
    }

}
