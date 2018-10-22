package com.buinak.curreq.ui.AddScreen;

import com.buinak.curreq.entities.CurreqEntity.Currency;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;

@Module
public class AddScreenObservablesModule {
    private PublishSubject<Currency> subject;

    public AddScreenObservablesModule(PublishSubject<Currency> subject) {
        this.subject = subject;
    }

    @Provides
    @Singleton
    PublishSubject<Currency> provideSubject(){
        return subject;
    }
}
