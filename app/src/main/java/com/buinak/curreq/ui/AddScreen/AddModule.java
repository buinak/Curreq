package com.buinak.curreq.ui.AddScreen;

import com.buinak.curreq.data.DataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AddModule {

    @Provides
    @Singleton
    AddRepository provideRepository(DataSource dataSource){
        return new AddRepository(dataSource);
    }
}
