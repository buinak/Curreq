package com.buinak.curreq.ui.AddScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddViewModel extends ViewModel {

    @Inject
    AddRepository repository;

    private MutableLiveData<List<List<CurrencyRecord>>> currencyList;

    private Disposable currencyListSubscription;

    public AddViewModel() {
        CurreqApplication.inject(this);

        currencyList = new MutableLiveData<>();
        currencyListSubscription = repository.getCurrencyList()
                .subscribeOn(Schedulers.io())
                .map(result -> separateIntoLists(result, 3))
                .subscribe(result -> currencyList.postValue(result));
    }

    public LiveData<List<List<CurrencyRecord>>> getCurrencyLists() {
        return currencyList;
    }

    private List<List<CurrencyRecord>> separateIntoLists(List<CurrencyRecord> list, int amountPerList){
        List<List<CurrencyRecord>> resultList = new ArrayList<>();
        List<CurrencyRecord> tempList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            tempList.add(list.get(i));
            if ((i + 1) % amountPerList == 0){
                resultList.add(tempList);
                tempList = new ArrayList<>();
            }
        }

        return resultList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        currencyListSubscription.dispose();
        currencyListSubscription = null;
    }
}
