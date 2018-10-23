package com.buinak.curreq.ui.SettingsScreen;

import com.buinak.curreq.data.DataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsScreenModule {
    @Provides
    @Singleton
    SettingsRepository provideSettingsRepository(DataSource dataSource) {
        return new SettingsRepository(dataSource);
    }


}
