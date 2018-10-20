package com.buinak.curreq.ui.MainScreen;

import com.buinak.curreq.data.DataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainScreenModule {

    @Provides
    @Singleton
    MainRepository provideRepository(DataSource dataSource){
        return new MainRepository(dataSource);
    }
}
