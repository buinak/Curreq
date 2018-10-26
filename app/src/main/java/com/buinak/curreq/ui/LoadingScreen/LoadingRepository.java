package com.buinak.curreq.ui.LoadingScreen;


import com.buinak.curreq.data.DataSource;

import java.util.Date;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;
import io.reactivex.subjects.PublishSubject;

public class LoadingRepository {

    private DataSource dataSource;

    private final int AMOUNT_OF_INITIALISATIONS = 2;

    private int completedAmount;

    private CompositeDisposable disposable;
    private Completable isReady;

    public LoadingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        disposable = new CompositeDisposable();

        completedAmount = 0;

        isReady = initialiseRepository();
    }

    public Completable getIsReady() {
        return isReady;
    }

    @NonNull
    private Completable initialiseRepository() {
        CompletableSubject completable = CompletableSubject.create();

        disposable.add(dataSource.initialiseRepositoryIfFirstStart()
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    this.completedAmount++;
                    if (completedAmount == AMOUNT_OF_INITIALISATIONS) {
                        completable.onComplete();
                    }
                }));

        disposable.add(dataSource.initialiseBitmaps()
                .subscribeOn(Schedulers.computation())
                .subscribe(() -> {
                    this.completedAmount++;
                    if (completedAmount == AMOUNT_OF_INITIALISATIONS) {
                        completable.onComplete();
                    }
                }));

        return completable;
    }
}
