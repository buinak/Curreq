package com.buinak.curreq.ui.AddScreen;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;

@Module
public class AddScreenObservablesModule {
    private PublishSubject<String> subject;

    public AddScreenObservablesModule(PublishSubject<String> subject) {
        this.subject = subject;
    }

    @Provides
    @Singleton
    PublishSubject<String> provideSubject(){
        return subject;
    }
}
