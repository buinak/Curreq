package com.buinak.curreq.ui.AddScreen;

import com.buinak.curreq.data.DataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;

@Module
public class AddModule {

    private PublishSubject<String> publishSubject;

    public AddModule() {
    }

    public AddModule(PublishSubject<String> publishSubject) {
        this.publishSubject = publishSubject;
    }

    @Provides
    @Singleton
    AddRepository provideRepository(DataSource dataSource){
        return new AddRepository(dataSource);
    }

    @Provides
    @Singleton
    PublishSubject<String> provideSubject(){
        return publishSubject;
    }
}
