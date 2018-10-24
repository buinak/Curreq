package com.buinak.curreq.data.Local;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDataSourceModule {
    @Provides
    @Singleton
    public LocalDataSource provideLocalDataSource(LocalCacheHandler localCacheHandler,
                                                  CurrencyDatabase currencyDatabase,
                                                  PreferencesRepository preferencesRepository) {
        return new LocalDataHandler(currencyDatabase, localCacheHandler, preferencesRepository);
    }

    @Provides
    @Singleton
    public LocalCacheHandler provideCacher(Context context) { return new LocalCacheHandler(context); }

    @Provides
    @Singleton
    PreferencesRepository providePreferencesRepository(Context context) { return new PreferencesRepository(context); }

    @Provides
    @Singleton
    public CurrencyDatabase provideCurrencyDatabase() {
        return new CurrencyDatabase();
    }

}
