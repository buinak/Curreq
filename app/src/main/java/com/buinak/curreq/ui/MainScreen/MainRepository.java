package com.buinak.curreq.ui.MainScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.graphics.Bitmap;

import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.CountryFlagBitmap;
import com.buinak.curreq.entities.CurreqEntity.Currency;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRate;
import com.buinak.curreq.utils.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import static com.buinak.curreq.utils.Constants.DAY_IN_MS;

public class MainRepository {

    private DataSource dataSource;

    private CompositeDisposable disposable;

    private PublishSubject<Boolean> isUpdating;
    private PublishSubject<String> messages;

    public MainRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        isUpdating = PublishSubject.create();
        messages = PublishSubject.create();

        disposable = new CompositeDisposable();
    }

    public MutableLiveData<List<CurrencyExchangeRate>> getSavedRatesLiveData() {
        MutableLiveData<List<CurrencyExchangeRate>> liveData = new MutableLiveData<>();
        disposable.add(dataSource.getAllSavedRecordsObservable().subscribe(results -> {
            disposable.add(dataSource.getAllBitmaps()
                    .subscribeOn(Schedulers.io())
                    .subscribe(bitmaps -> {
                        if (bitmaps.size() != 0) {
                            if (bitmaps.get(0) != null) {
                                liveData.postValue(wrapRateRecords(bitmaps, results));
                            } else {
                                liveData.postValue(null);
                            }
                        } else {
                            liveData.postValue(null);
                        }
                    }));
        }));
        return liveData;
    }

    private List<CurrencyExchangeRate> wrapRateRecords(List<CountryFlagBitmap> wrappers,
                                                       List<CurrencyExchangeRate> records) {
        HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();
        for (CountryFlagBitmap wrapper :
                wrappers) {
            bitmapHashMap.put(wrapper.getCode(), wrapper.getBitmap());
        }
        List<CurrencyExchangeRate> currencyExchangeRateWithBitmapsAndIds = new ArrayList<>();
        for (CurrencyExchangeRate currencyExchangeRateWithId :
                records) {
            Currency baseCurrency = new Currency(currencyExchangeRateWithId.getBaseCurrency(),
                    bitmapHashMap.get(currencyExchangeRateWithId.getBaseCurrency().getCode().substring(0, 2).toLowerCase()));

            Currency currency = new Currency(currencyExchangeRateWithId.getCurrency(),
                    bitmapHashMap.get(currencyExchangeRateWithId.getCurrency().getCode().substring(0, 2).toLowerCase()));

            CurrencyExchangeRate currencyExchangeRateWithBitmapsAndId = new CurrencyExchangeRate(currencyExchangeRateWithId.getId(),
                    baseCurrency, currency, currencyExchangeRateWithId.getRate());
            currencyExchangeRateWithBitmapsAndIds.add(currencyExchangeRateWithBitmapsAndId);
        }
        return currencyExchangeRateWithBitmapsAndIds;
    }

    public void replaceRecordWithNew(String recordId) {
        dataSource.swapRecord(recordId);
    }

    public void onResetPressed() {
        dataSource.resetAllSavedRecords();
    }

    public void onUpdatePressed() {
        disposable.add(dataSource.refreshRecords().subscribe(result -> {
            isUpdating.onNext(false);
            if (result) {
                messages.onNext(Constants.UPDATE_SUCCESSFUL_STRING);
            } else {
                messages.onNext(Constants.TOO_FREQUENT_UPDATES_STRING);
            }
        }));
    }

    public Observable<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public LiveData<Date> getLastUpdatedLiveData() {
        MutableLiveData<Date> mutableLiveData = new MutableLiveData<>();
        disposable.add(dataSource.getLatestRecordDateObservable()
                .subscribe(mutableLiveData::postValue));
        return mutableLiveData;
    }

    public void checkDailyUpdates() {
        if (dataSource.isDailyUpdatesOn()) {
            disposable.add(dataSource.getLatestRecordDateObservable()
                    .subscribe(date -> {
                        Date dayAgo = new Date(System.currentTimeMillis() - DAY_IN_MS);
                        if (date.getTime() < dayAgo.getTime()) {
                            dataSource.updateRecords().subscribe();
                        }
                    }));
        }
    }

    public PublishSubject<String> getMessages() {
        return messages;
    }
}
