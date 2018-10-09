package com.buinak.curreq.data.Remote;

import com.buinak.curreq.data.Remote.Fixer.ApiModule;
import com.buinak.curreq.data.Remote.Fixer.FixerIOApi;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module (includes = {ApiModule.class})
public class RemoteDataSourceModule {

    @Inject
    FixerIOApi api;

    @Inject
    public RemoteDataSourceModule(FixerIOApi api) {
        this.api = api;
    }

    @Provides
    public RemoteDataSource provideRemoteDataSource() {
        return new CurrencyRepository(api);
    }
}
