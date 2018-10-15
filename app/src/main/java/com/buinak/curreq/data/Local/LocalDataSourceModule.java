package com.buinak.curreq.data.Local;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDataSourceModule {

    private Context context;

    public LocalDataSourceModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public LocalDataSource provideLocalDataSource(LocalCacheHandler localCacheHandler, CurrencyDatabase currencyDatabase) {
        return new LocalDataHandler(currencyDatabase, localCacheHandler);
    }

    @Provides
    public LocalCacheHandler provideCacher() { return new LocalCacheHandler(context); }

    @Provides
    public CurrencyDatabase provideCurrencyDatabase() {
        return new CurrencyDatabase();
    }

}
