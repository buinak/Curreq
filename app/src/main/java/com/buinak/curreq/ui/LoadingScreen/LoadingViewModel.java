package com.buinak.curreq.ui.LoadingScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.buinak.curreq.application.CurreqApplication;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class LoadingViewModel extends ViewModel {

    private MutableLiveData<Boolean> isReadyLiveData;

    private Disposable isReadySubscription;

    @Inject
    LoadingRepository repository;

    public LoadingViewModel() {
        isReadyLiveData = new MutableLiveData<>();

        CurreqApplication.inject(this);
        isReadySubscription = repository.getIsReady().subscribe(result -> isReadyLiveData.postValue(result));
    }

    public LiveData<Boolean> getIsReady(){
        return isReadyLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.dispose();
        if (isReadySubscription != null){
            isReadySubscription.dispose();
            isReadySubscription = null;
        }
    }
}
