package com.buinak.curreq.ui.MainScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.entities.CurreqEntity.SavedRateRecordBitmapWrapper;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    @Inject
    MainRepository mainRepository;

    private LiveData<List<SavedRateRecordBitmapWrapper>> savedRateRecords;

    public MainViewModel() {
        CurreqApplication.inject(this);

        savedRateRecords = mainRepository.getSavedRatesLiveData();
    }

    public LiveData<List<SavedRateRecordBitmapWrapper>> getSavedRateRecords() {
        return savedRateRecords;
    }

    public void onRateRecordSwapped(String recordId){
        mainRepository.replaceRecordWithNew(recordId);
    }

}
