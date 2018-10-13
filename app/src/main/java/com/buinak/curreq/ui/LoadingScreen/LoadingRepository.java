package com.buinak.curreq.ui.LoadingScreen;

import android.arch.lifecycle.MutableLiveData;

import com.buinak.curreq.data.DataSource;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoadingRepository{

    DataSource dataSource;
    private Disposable openRequest;

    private MutableLiveData<Boolean> isReady;

    public LoadingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        isReady = new MutableLiveData<>();

        openRequest = dataSource.initialiseRepositoryIfFirstStart()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> isReady.postValue(result));
    }

    public Single<Boolean> getIsReady() {
        return dataSource.initialiseRepositoryIfFirstStart();
    }

    public void dispose(){
        if (openRequest != null){
            openRequest.dispose();
            openRequest = null;
        }
    }
}
