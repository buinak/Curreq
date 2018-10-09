package com.buinak.curreq.data.Remote;

import com.buinak.curreq.data.Remote.Fixer.ApiModule;
import com.buinak.curreq.data.Remote.Fixer.FixerIOApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (includes = {ApiModule.class})
public class RemoteDataSourceModule {

    @Provides
    @Singleton
    public RemoteDataSource provideRemoteDataSource(FixerIOApi api) {
        return new CurrencyRepository(api);
    }
}
