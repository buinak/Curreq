package com.buinak.curreq.di;

import android.content.Context;

import com.buinak.curreq.application.ApplicationModule;
import com.buinak.curreq.data.RepositoryModule;
import com.buinak.curreq.ui.AddScreen.AddRepository;
import com.buinak.curreq.ui.AddScreen.AddScreenModule;
import com.buinak.curreq.ui.AddScreen.AddViewModel;
import com.buinak.curreq.ui.LoadingScreen.LoadingRepository;
import com.buinak.curreq.ui.LoadingScreen.LoadingScreenModule;
import com.buinak.curreq.ui.LoadingScreen.LoadingViewModel;
import com.buinak.curreq.ui.MainScreen.MainRepository;
import com.buinak.curreq.ui.MainScreen.MainScreenModule;
import com.buinak.curreq.ui.MainScreen.MainViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component (modules = {RepositoryModule.class, AddScreenModule.class, LoadingScreenModule.class, MainScreenModule.class, ApplicationModule.class})
@Singleton
public interface ApplicationComponent {
    AddRepository provideAddRepository();
    void inject(AddViewModel viewModel);

    LoadingRepository provideLoadingRepository();
    void inject(LoadingViewModel viewModel);

    MainRepository provideMainRepository();
    void inject(MainViewModel viewModel);

    Context provideContext();
}
