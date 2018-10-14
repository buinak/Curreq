package com.buinak.curreq.ui.AddScreen;

import com.buinak.curreq.data.DataSource;

import dagger.Module;
import dagger.Provides;

@Module
public class AddModule {

    @Provides
    AddRepository provideRepository(DataSource dataSource){
        return new AddRepository(dataSource);
    }
}
