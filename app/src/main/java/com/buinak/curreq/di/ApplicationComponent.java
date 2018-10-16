package com.buinak.curreq.di;

import android.content.Context;

import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.data.RepositoryModule;
import com.buinak.curreq.ui.AddScreen.AddModule;
import com.buinak.curreq.ui.AddScreen.AddRepository;
import com.buinak.curreq.ui.AddScreen.AddViewModel;
import com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView.CurrencyViewHolder;
import com.buinak.curreq.ui.LoadingScreen.LoadingModule;
import com.buinak.curreq.ui.LoadingScreen.LoadingRepository;
import com.buinak.curreq.ui.LoadingScreen.LoadingViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component (modules = {RepositoryModule.class, AddModule.class, LoadingModule.class, ApplicationModule.class})
@Singleton
public interface ApplicationComponent {
    AddRepository provideAddRepository();
    void inject(AddViewModel viewModel);

    LoadingRepository provideLoadingRepository();
    void inject(LoadingViewModel viewModel);

    Context provideContext();
}
