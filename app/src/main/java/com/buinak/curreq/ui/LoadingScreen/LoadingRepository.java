package com.buinak.curreq.ui.LoadingScreen;

import com.buinak.curreq.data.DataSource;

import io.reactivex.Completable;

public class LoadingRepository{

    private DataSource dataSource;

    public LoadingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Completable getIsReady() {
        return dataSource.initialiseRepositoryIfFirstStart();
    }

}
