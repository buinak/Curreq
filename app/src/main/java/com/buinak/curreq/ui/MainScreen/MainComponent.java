package com.buinak.curreq.ui.MainScreen;

import com.buinak.curreq.ui.MainScreen.RatesRecyclerView.RatesViewHolder;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.subjects.PublishSubject;

@Component(modules = MainScreenObservableModule.class)
@Singleton
public interface MainComponent {

    PublishSubject<String> provideSubject();

    void inject(RatesViewHolder viewHolder);
}
