package com.buinak.curreq.ui.AddScreen;

import com.buinak.curreq.data.DataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;

@Module
public class AddScreenModule {

    @Provides
    @Singleton
    AddRepository provideRepository(DataSource dataSource){
        return new AddRepository(dataSource);
    }

}
