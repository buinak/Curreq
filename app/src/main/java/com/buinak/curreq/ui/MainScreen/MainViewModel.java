package com.buinak.curreq.ui.MainScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

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
    private MutableLiveData<String> toastLiveData;
    private MutableLiveData<Boolean> isUpdating;

    CompositeDisposable disposable;

    public MainViewModel() {
        CurreqApplication.inject(this);
        disposable = new CompositeDisposable();

        toastLiveData = new MutableLiveData<>();
        disposable.add(mainRepository.getMessages()
                .subscribe(message -> toastLiveData.postValue(message)));

        savedRateRecords = mainRepository.getSavedRatesLiveData();

        isUpdating = new MutableLiveData<>();
        isUpdating.postValue(false);
        disposable.add(mainRepository.getIsUpdating()
                .subscribe(val -> {
                    if (!val) {
                        isUpdating.postValue(val);
                    }
                }));

        lastUpdatedDate = mainRepository.getLastUpdatedLiveData();
    }

    public void onBind() {
        mainRepository.checkDailyUpdates();
    }

    public LiveData<List<CurrencyExchangeRate>> getSavedRateRecords() {
        return savedRateRecords;
    }

    public void onRateRecordSwapped(String recordId) {
        mainRepository.replaceRecordWithNew(recordId);
    }

    public void onResetPressed() {
        mainRepository.onResetPressed();
    }

    public void onUpdatePressed() {
        mainRepository.onUpdatePressed();
    }

    public LiveData<Date> getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public MutableLiveData<String> getToastLiveData() {
        return toastLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        toastLiveData = null;
        savedRateRecords = null;
        lastUpdatedDate = null;
        isUpdating = null;
    }
}
