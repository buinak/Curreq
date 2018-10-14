package com.buinak.curreq.ui.AddScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddViewModel extends ViewModel {

    @Inject
    AddRepository repository;

    private MutableLiveData<List<CurrencyRecord>> currencyList;

    private Disposable currencyListSubscription;

    public AddViewModel() {
        CurreqApplication.inject(this);

        currencyList = new MutableLiveData<>();
        currencyListSubscription = repository.getCurrencyList()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> currencyList.postValue(result));
    }

    public LiveData<List<CurrencyRecord>> getCurrencyList() {
        return currencyList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        currencyListSubscription.dispose();
        currencyListSubscription = null;
    }
}
