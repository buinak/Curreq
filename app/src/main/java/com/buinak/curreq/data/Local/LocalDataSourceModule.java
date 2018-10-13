package com.buinak.curreq.data.Local;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDataSourceModule {
    @Provides
    static LocalDataSource provideLocalDataSource() {
        return new CurrencyDatabase();
    }
}
