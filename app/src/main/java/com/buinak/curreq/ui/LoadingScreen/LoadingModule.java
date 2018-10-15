package com.buinak.curreq.ui.LoadingScreen;

import com.buinak.curreq.data.DataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoadingModule {

    @Provides
    @Singleton
    LoadingRepository provideRepository(DataSource dataSource){
        return new LoadingRepository(dataSource);
    }
}
