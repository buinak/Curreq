package com.buinak.curreq.di;

import android.content.Context;

import com.buinak.curreq.application.ContextModule;
import com.buinak.curreq.data.DataRepositoriesModule;
import com.buinak.curreq.ui.AddScreen.AddRepository;
import com.buinak.curreq.ui.AddScreen.AddViewModel;
import com.buinak.curreq.ui.LoadingScreen.LoadingRepository;
import com.buinak.curreq.ui.LoadingScreen.LoadingViewModel;
import com.buinak.curreq.ui.MainScreen.MainRepository;
import com.buinak.curreq.ui.MainScreen.MainViewModel;
import com.buinak.curreq.ui.SettingsScreen.SettingsRepository;
import com.buinak.curreq.ui.SettingsScreen.SettingsViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component (modules = {DataRepositoriesModule.class, ApplicationRepositoriesModule.class, ContextModule.class})
@Singleton
public interface ApplicationComponent {
    void inject(AddViewModel viewModel);
    void inject(LoadingViewModel viewModel);
    void inject(MainViewModel viewModel);
    void inject(SettingsViewModel viewModel);
}
