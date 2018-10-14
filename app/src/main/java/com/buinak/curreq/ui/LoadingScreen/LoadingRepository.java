package com.buinak.curreq.ui.LoadingScreen;

import com.buinak.curreq.data.DataSource;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class LoadingRepository{

    private DataSource dataSource;

    public LoadingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Completable getIsReady() {
        return dataSource.initialiseRepositoryIfFirstStart()
                .subscribeOn(Schedulers.io());
    }

}
