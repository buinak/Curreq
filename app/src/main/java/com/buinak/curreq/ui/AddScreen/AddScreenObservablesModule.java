package com.buinak.curreq.ui.AddScreen;

import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;

@Module
public class AddScreenObservablesModule {
    private PublishSubject<CurrencyRecord> subject;

    public AddScreenObservablesModule(PublishSubject<CurrencyRecord> subject) {
        this.subject = subject;
    }

    @Provides
    @Singleton
    PublishSubject<CurrencyRecord> provideSubject(){
        return subject;
    }
}
