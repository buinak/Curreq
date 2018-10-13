package com.buinak.curreq.ui.LoadingScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.buinak.curreq.application.CurreqApplication;

public class LoadingViewModel extends ViewModel {

    private MutableLiveData<Boolean> isReadyLiveData;

    LoadingRepository repository;

    public LoadingViewModel() {
        isReadyLiveData = new MutableLiveData<>();

        //we inject so that the repository does not know about any android components
        //considering Application class is an Android component
        repository = new LoadingRepository(this);
        CurreqApplication.getRepositoryComponent(repository).inject(repository);
        repository.initialise();
    }

    public LiveData<Boolean> getIsReady(){
        return isReadyLiveData;
    }

    public void setIsReady(Boolean isReady){
        isReadyLiveData.postValue(isReady);
    }
}
