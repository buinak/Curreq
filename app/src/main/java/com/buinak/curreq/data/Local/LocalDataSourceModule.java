package com.buinak.curreq.data.Local;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDataSourceModule {
    @Provides
    @Singleton
    public LocalDataSource provideLocalDataSource(LocalCacheHandler localCacheHandler, CurrencyDatabase currencyDatabase) {
        return new LocalDataHandler(currencyDatabase, localCacheHandler);
    }

    @Provides
    public LocalCacheHandler provideCacher(Context context) { return new LocalCacheHandler(context); }

    @Provides
    public CurrencyDatabase provideCurrencyDatabase() {
        return new CurrencyDatabase();
    }

}
