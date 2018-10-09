package com.buinak.curreq.data.Local;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDataSourceModule {
    @Provides
    @Singleton
    static LocalDataSource provideLocalDataSource() {
        return new CurrencyDatabase();
    }
}
