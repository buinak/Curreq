package com.buinak.curreq.ui.AddScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Pair;

import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.entities.CurreqEntity.Currency;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddViewModel extends ViewModel {

    @Inject
    AddRepository repository;

    private MutableLiveData<List<Currency>> currencyList;
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

    public LiveData<List<Currency>> getCurrencyLists() {
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
