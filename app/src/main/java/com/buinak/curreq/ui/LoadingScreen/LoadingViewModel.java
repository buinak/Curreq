package com.buinak.curreq.ui.LoadingScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class LoadingViewModel extends ViewModel {

    private MutableLiveData<Boolean> isReadyLiveData;

    private LoadingRepository repository;

    public LoadingViewModel() {
        isReadyLiveData = new MutableLiveData<>();
        repository = new LoadingRepository(this);
    }

    public LiveData<Boolean> getIsReady(){
        return isReadyLiveData;
    }

    public void setIsReady(Boolean isReady){
        isReadyLiveData.postValue(isReady);
    }
}
