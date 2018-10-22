package com.buinak.curreq.ui.AddScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Pair;

import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.entities.CurreqEntity.Currency;
import com.buinak.curreq.entities.CurreqEntity.CurrencyCountryFlagWrapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddViewModel extends ViewModel {

    @Inject
    AddRepository repository;

    private MutableLiveData<List<CurrencyCountryFlagWrapper>> currencyList;
    private MutableLiveData<Boolean> finished;

    private CompositeDisposable subscriptions;

    public AddViewModel() {
        CurreqApplication.inject(this);

        subscriptions = new CompositeDisposable();

        initialiseLiveData();
    }

    public void reset(){
        repository.reset();
        initialiseLiveData();
    }

    private void initialiseLiveData() {
        currencyList = new MutableLiveData<>();
        subscriptions.add(repository.getBitmappedCurrencyRecords()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> currencyList.postValue(result)));

        finished = new MutableLiveData<>();
        subscriptions.add(repository.getFinished()
                .subscribe(() -> finished.postValue(true)));
    }

    public LiveData<List<CurrencyCountryFlagWrapper>> getCurrencyLists() {
        return currencyList;
    }

    public LiveData<Boolean> getFinished() {
        return finished;
    }

    public void onRatePairSelected(Pair<Currency, Currency> ratePair){
        repository.saveRatePair(ratePair);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        subscriptions.dispose();
        subscriptions = null;

        repository.dispose();
    }
}
