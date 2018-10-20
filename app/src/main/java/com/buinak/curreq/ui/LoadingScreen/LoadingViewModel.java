package com.buinak.curreq.ui.LoadingScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.entities.ActivityState;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class LoadingViewModel extends ViewModel {

    private MutableLiveData<Boolean> isReadyLiveData;
    private MutableLiveData<ActivityState> activityStateLiveData;

    private Disposable isReadySubscription;

    @Inject
    LoadingRepository repository;

    public LoadingViewModel() {
        isReadyLiveData = new MutableLiveData<>();
        activityStateLiveData = new MutableLiveData<>();

        CurreqApplication.inject(this);

        isReadySubscription = repository.getIsReady()
                .subscribe(() -> {
                            isReadyLiveData.postValue(true);
                            activityStateLiveData.postValue(ActivityState.FINISHED);
                        }
                );
    }

    public LiveData<Boolean> getIsReady() {
        return isReadyLiveData;
    }

    public LiveData<ActivityState> getActivityState() {
        return activityStateLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (isReadySubscription != null) {
            isReadySubscription.dispose();
            isReadySubscription = null;
        }
    }
}
