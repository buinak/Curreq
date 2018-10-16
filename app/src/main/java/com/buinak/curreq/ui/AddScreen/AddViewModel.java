package com.buinak.curreq.ui.AddScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.entities.CurreqEntity.BitmappedCurrencyRecord;
import com.buinak.curreq.utils.Constants;
import com.buinak.curreq.utils.ListUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddViewModel extends ViewModel {

    @Inject
    AddRepository repository;

    private MutableLiveData<List<List<BitmappedCurrencyRecord>>> currencyList;

    private Disposable currencyListSubscription;

    public AddViewModel() {
        CurreqApplication.inject(this);

        currencyList = new MutableLiveData<>();
        currencyListSubscription = repository.getBitmappedCurrencyRecords()
                .subscribeOn(Schedulers.io())
                .map(result -> ListUtils.separateIntoLists(result, Constants.ADD_SCREEN_AMOUNT_OF_CURRENCIES_PER_ROW))
                .subscribe(result -> currencyList.postValue(result));
    }

    public LiveData<List<List<BitmappedCurrencyRecord>>> getCurrencyLists() {
        return currencyList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        currencyListSubscription.dispose();
        currencyListSubscription = null;
    }
}
