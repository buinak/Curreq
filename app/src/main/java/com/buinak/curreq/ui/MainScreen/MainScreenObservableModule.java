package com.buinak.curreq.ui.MainScreen;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;

@Module
public class MainScreenObservableModule {

    private PublishSubject<String> publishSubject;

    public MainScreenObservableModule(PublishSubject<String> publishSubject) {
        this.publishSubject = publishSubject;
    }

    @Provides
    @Singleton
    PublishSubject<String> provideSubject(){
        return publishSubject;
    }

}
