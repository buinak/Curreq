package com.buinak.curreq.di;

import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.ui.AddScreen.AddRepository;
import com.buinak.curreq.ui.LoadingScreen.LoadingRepository;
import com.buinak.curreq.ui.MainScreen.MainRepository;
import com.buinak.curreq.ui.SettingsScreen.SettingsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationRepositoriesModule {
    @Provides
    @Singleton
    LoadingRepository provideLoadingRepository(DataSource dataSource){
        return new LoadingRepository(dataSource);
    }

    @Provides
    @Singleton
    AddRepository provideAddRepository(DataSource dataSource){
        return new AddRepository(dataSource);
    }

    @Provides
    @Singleton
    MainRepository provideMainRepository(DataSource dataSource){
        return new MainRepository(dataSource);
    }

    @Provides
    @Singleton
    SettingsRepository provideSettingsRepository(DataSource dataSource) {
        return new SettingsRepository(dataSource);
    }
}
