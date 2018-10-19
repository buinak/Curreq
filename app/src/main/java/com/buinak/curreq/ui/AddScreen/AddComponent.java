package com.buinak.curreq.ui.AddScreen;

import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView.CurrencyViewHolder;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.subjects.PublishSubject;

@Component (modules = AddScreenObservablesModule.class)
@Singleton
public interface AddComponent {
    PublishSubject<CurrencyRecord> provideSubject();

    void inject(CurrencyViewHolder viewHolder);
}
