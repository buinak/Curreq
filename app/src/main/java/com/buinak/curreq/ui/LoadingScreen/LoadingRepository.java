package com.buinak.curreq.ui.LoadingScreen;

import com.buinak.curreq.data.DataSource;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;

public class LoadingRepository {

    private DataSource dataSource;

    private final int AMOUNT_OF_INITIALISATIONS = 2;

    private int completedAmount;

    private CompositeDisposable disposable;

    public LoadingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        disposable = new CompositeDisposable();

        completedAmount = 0;
    }

    public Completable getIsReady() {
        CompletableSubject completable = CompletableSubject.create();

        disposable.add(dataSource.initialiseRepositoryIfFirstStart()
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    this.completedAmount++;
                    if (completedAmount == AMOUNT_OF_INITIALISATIONS){
                        completable.onComplete();
                    }
                }));

        disposable.add(dataSource.initialiseBitmaps()
                .subscribeOn(Schedulers.computation())
                .subscribe(() -> {
                    this.completedAmount++;
                    if (completedAmount == AMOUNT_OF_INITIALISATIONS){
                        completable.onComplete();
                    }
                }));

        return completable;
    }

    public Completable getAreBitmapsCached() {
        return dataSource.initialiseBitmaps();
    }
}
