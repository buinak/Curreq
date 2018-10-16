package com.buinak.curreq.ui.LoadingScreen;

import com.buinak.curreq.data.DataSource;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;

public class LoadingRepository {

    private DataSource dataSource;

    private final int AMOUNT_OF_INITIALISATIONS = 2;

    private int completedAmount;

    public LoadingRepository(DataSource dataSource) {
        this.dataSource = dataSource;

        completedAmount = 0;
    }

    public Completable getIsReady() {
        CompletableSubject completable = CompletableSubject.create();

        dataSource.initialiseRepositoryIfFirstStart()
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    this.completedAmount++;
                    if (completedAmount == AMOUNT_OF_INITIALISATIONS){
                        completable.onComplete();
                    }
                });

        dataSource.initialiseBitmaps()
                .subscribeOn(Schedulers.computation())
                .subscribe(() -> {
                    this.completedAmount++;
                    if (completedAmount == AMOUNT_OF_INITIALISATIONS){
                        completable.onComplete();
                    }
                });

        return completable;
    }

    public Completable getAreBitmapsCached() {
        return dataSource.initialiseBitmaps();
    }
}
