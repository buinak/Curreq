package com.buinak.curreq.ui.LoadingScreen;

import com.buinak.curreq.data.DataSource;

import dagger.Module;
import dagger.Provides;

@Module
public class LoadingModule {

    @Provides
    LoadingRepository provideRepository(DataSource dataSource){
        return new LoadingRepository(dataSource);
    }
}
