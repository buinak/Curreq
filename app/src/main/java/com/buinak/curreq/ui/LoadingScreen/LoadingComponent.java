package com.buinak.curreq.ui.LoadingScreen;

import com.buinak.curreq.data.RepositoryComponent;

import javax.inject.Singleton;

import dagger.Component;

@Component (dependencies = RepositoryComponent.class, modules = {LoadingModule.class})
@Singleton
public interface LoadingComponent {

    LoadingRepository provideLoadingRepository();


    void inject(LoadingViewModel viewModel);
}
