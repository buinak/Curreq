package com.buinak.curreq.data.Remote;

import com.buinak.curreq.data.Remote.Fixer.ApiModule;
import com.buinak.curreq.data.Remote.Fixer.FixerIOApi;

import dagger.Module;
import dagger.Provides;

@Module (includes = {ApiModule.class})
public class RemoteDataSourceModule {

    @Provides
    static RemoteDataSource provideRemoteDataSource(FixerIOApi api) {
        return new CurrencyRepository(api);
    }
}
