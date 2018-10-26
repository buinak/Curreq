package com.buinak.curreq.ui.MainScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRate;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    @Inject
    MainRepository mainRepository;

    private LiveData<List<CurrencyExchangeRate>> savedRateRecords;
    private LiveData<Date> lastUpdatedDate;
    private MutableLiveData<Boolean> isUpdating;

    public MainViewModel() {
        CurreqApplication.inject(this);

        savedRateRecords = mainRepository.getSavedRatesLiveData();
        savedRateRecords.observeForever(r -> isUpdating.postValue(false));

        lastUpdatedDate = mainRepository.getLastUpdatedLiveData();
        isUpdating = new MutableLiveData<>();
        isUpdating.postValue(false);
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
        isUpdating.postValue(true);
    }

    public LiveData<Date> getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }
}
